package br.com.sindy.domain.service;

import br.com.sindy.domain.dto.FileResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface AssociateFileService {
    FileResponseDto findByAssociateId(Long id);

    Page<FileResponseDto> findByAssociateId(Long associateId, Pageable pageable);

    FileResponseDto saveProfilePicture(Long associateId, MultipartFile file);

    void deleteProfilePicture(Long associateId);

    FileResponseDto saveDocument(Long associateId, MultipartFile file);

    FileResponseDto findByAssociateIdAndDocumentId(Long associateId, Long documentId);

    void deleteDocument(Long associateId, Long documentId);
}
