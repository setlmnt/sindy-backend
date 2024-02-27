package br.com.sindy.domain.service.impl;

import br.com.sindy.core.annotation.Log;
import br.com.sindy.domain.dto.FileResponseDto;
import br.com.sindy.domain.entity.File;
import br.com.sindy.domain.enums.ErrorEnum;
import br.com.sindy.domain.exception.ApiException;
import br.com.sindy.domain.mapper.FileMapper;
import br.com.sindy.domain.repository.FileRepository;
import br.com.sindy.domain.service.FileService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@Log
public class FileServiceImpl implements FileService {
    private final FileMapper fileMapper;
    private final FileRepository fileRepository;

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Value("${app.upload.images.dir}")
    private String uploadProfilePictureDir;

    @Value("${app.upload.docs.dir}")
    private String uploadDocsDir;

    @PostConstruct
    public void postConstruct() {
        log.info("Ensuring upload directory exists");
        ensureUploadDirExists(uploadProfilePictureDir);
        ensureUploadDirExists(uploadDocsDir);
    }

    public Page<FileResponseDto> findAll(Pageable pageable) {
        log.info("Listing all images");
        return fileRepository.findAll(pageable).map(fileMapper::toResponseDto);
    }

    public void delete(Long id, String uploadDir) {
        log.info("Deleting image with id {}", id);
        File file = fileRepository.getReferenceById(id);
        file.delete();

        log.info("Deleting associate photo from directory");
        deleteFile(file.getArchiveName(), uploadDir);
    }

    public Resource load(String name) {
        log.info("Loading file {}", name);
        Path uploadDirPath = Paths.get(uploadDir);

        try (Stream<Path> paths = Files.walk(uploadDirPath)) {
            Path filePath = paths
                    .filter(path -> path.getFileName().toString().equals(name))
                    .findFirst()
                    .orElseThrow(() -> new FileNotFoundException("File not found: " + name));

            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
        } catch (FileNotFoundException e) {
            log.error("File not found", e);
            throw new ApiException(ErrorEnum.FILE_NOT_FOUND);
        } catch (IOException e) {
            log.error("An error occurred while loading the file", e);
            throw new ApiException(ErrorEnum.ERROR_WHILE_LOADING_FILE);
        }

        return null;
    }

    public void store(MultipartFile file, String fileName, String uploadDir) {
        try {
            log.info("Storing file {}", fileName);
            Path targetPath = Paths.get(uploadDir).resolve(fileName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("An error occurred while storing the file", e);
            throw new ApiException(ErrorEnum.ERROR_WHILE_STORING_FILE);
        }
    }

    private void deleteFile(String fileName, String uploadDir) {
        try {
            log.info("Deleting file {}", fileName);
            Path filePath = Paths.get(uploadDir).resolve(fileName);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            log.error("An error occurred while deleting the file", e);
            throw new ApiException(ErrorEnum.ERROR_WHILE_DELETE_FILE);
        }
    }

    private void ensureUploadDirExists(String dir) {
        Path path = Paths.get(dir);
        if (!Files.exists(path)) {
            log.info("Creating upload directory at {}", dir);
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                log.error("An error occurred while creating the upload directory", e);
                throw new ApiException(ErrorEnum.ERROR_WHILE_CREATING_DIRECTORY);
            }
        }
    }
}
