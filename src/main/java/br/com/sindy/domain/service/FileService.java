package br.com.sindy.domain.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    void delete(Long id, String uploadDir);

    void deleteFile(String fileName, String uploadDir);

    Resource load(String name, String uploadDir);

    void store(MultipartFile file, String fileName, String uploadDir);
}
