package com.ifba.educampo.domain.service;

import com.ifba.educampo.domain.dto.FileResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface AssociatePhotoService {
    FileResponseDto findByAssociateId(Long id);

    FileResponseDto save(Long associateId, MultipartFile file, String uploadDir);

    void delete(Long associateId, String uploadDir);

    Page<FileResponseDto> findDocumentsByAssociateId(Long associateId, Pageable pageable);

    FileResponseDto saveDocument(Long associateId, MultipartFile file, String uploadDir);

    FileResponseDto findDocumentByAssociateIdAndDocumentId(Long associateId, Long documentId);

    void deleteDocument(Long associateId, Long documentId, String uploadDir);
}
