package com.ifba.educampo.domain.service;

import com.ifba.educampo.domain.dto.FileResponseDto;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    void postConstruct();

    Page<FileResponseDto> findAll(Pageable pageable);

    void delete(Long id, String uploadDir);

    Resource load(String name);

    void store(MultipartFile file, String fileName, String uploadDir);
}
