package br.com.sindy.domain.service.impl;

import br.com.sindy.core.annotation.Log;
import br.com.sindy.domain.entity.File;
import br.com.sindy.domain.enums.ErrorEnum;
import br.com.sindy.domain.exception.ApiException;
import br.com.sindy.domain.repository.FileRepository;
import br.com.sindy.domain.service.FileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
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
@Slf4j
@Log
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;

    @Override
    @Transactional
    public void delete(Long id, String uploadDir) {
        log.info("Deleting image with id {}", id);
        File file = fileRepository.getReferenceById(id);
        file.delete();

        log.info("Deleting associate photo from directory");
        deleteFile(file.getArchiveName(), uploadDir);
    }

    @Override
    public Resource load(String name, String uploadDir) {
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

    @Override
    @Transactional
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

    @Override
    @Transactional
    public void deleteFile(String fileName, String uploadDir) {
        try {
            log.info("Deleting file {}", fileName);
            Path filePath = Paths.get(uploadDir).resolve(fileName);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            log.error("An error occurred while deleting the file", e);
            throw new ApiException(ErrorEnum.ERROR_WHILE_DELETE_FILE);
        }
    }
}
