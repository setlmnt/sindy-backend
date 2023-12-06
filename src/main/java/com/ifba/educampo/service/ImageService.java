package com.ifba.educampo.service;

import com.ifba.educampo.annotation.Log;
import com.ifba.educampo.dto.ImageResponseDto;
import com.ifba.educampo.mapper.ImageMapper;
import com.ifba.educampo.model.entity.Image;
import com.ifba.educampo.repository.ImageRepository;
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
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@Log
public class ImageService {
    private final ImageMapper imageMapper;
    private final ImageRepository imageRepository;

    @Value("${app.upload.image.dir}")
    private String uploadDir;

    @PostConstruct
    public void ensureUploadDirExists() {
        log.info("Ensuring upload directory exists");
        Path path = Paths.get(uploadDir);
        if (!Files.exists(path)) {
            log.info("Creating upload directory at {}", uploadDir);
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                log.error("An error occurred while creating the upload directory", e);
                throw new RuntimeException("An error occurred while creating the upload directory", e);
            }
        }
    }

    public Page<ImageResponseDto> findAll(Pageable pageable) {
        log.info("Listing all images");
        return imageRepository.findAll(pageable).map(imageMapper::toResponseDto);
    }

    public void delete(Long id) {
        log.info("Deleting image with id {}", id);
        Image image = imageRepository.getReferenceById(id);
        image.delete();

        log.info("Deleting associate photo from directory");
        deleteFile(image.getArchiveName());
    }

    public Resource load(String name) {
        try {
            log.info("Loading file {}", name);
            Path filePath = Paths.get(uploadDir).resolve(name);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }

            log.error("File not found: " + name);
            throw new FileNotFoundException("File not found: " + name);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        } catch (MalformedURLException e) {
            log.error("An error occurred while loading the file", e);
            throw new RuntimeException("An error occurred while loading the file", e);
        }
    }

    public void store(MultipartFile file, String fileName) {
        try {
            log.info("Storing file {}", fileName);
            Path targetPath = Paths.get(uploadDir).resolve(fileName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("An error occurred while storing the file", e);
            throw new RuntimeException("An error occurred while storing the file", e);
        }
    }

    private void deleteFile(String fileName) {
        try {
            log.info("Deleting file {}", fileName);
            Path filePath = Paths.get(uploadDir).resolve(fileName);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            log.error("An error occurred while storing the file", e);
            throw new RuntimeException("An error occurred while deleting the file", e);
        }
    }
}
