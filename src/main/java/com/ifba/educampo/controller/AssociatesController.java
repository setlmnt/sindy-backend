package com.ifba.educampo.controller;

import com.ifba.educampo.annotation.Log;
import com.ifba.educampo.dto.FileResponseDto;
import com.ifba.educampo.dto.associate.AssociatePostDto;
import com.ifba.educampo.dto.associate.AssociatePutDto;
import com.ifba.educampo.dto.associate.AssociateResponseDto;
import com.ifba.educampo.enums.PeriodEnum;
import com.ifba.educampo.service.associate.AssociatePhotoService;
import com.ifba.educampo.service.associate.AssociateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Associates", description = "Associates API")
@RestController
@RequestMapping(value = "/api/v1/associates")
@SecurityRequirements({
        @SecurityRequirement(name = "bearerAuth"),
        @SecurityRequirement(name = "cookieAuth")
})
@Log
@RequiredArgsConstructor
public class AssociatesController { // Classe de controle para o Associado
    private final AssociateService associateService;
    private final AssociatePhotoService associatePhotoService;

    @Value("${app.upload.images.dir}")
    private String uploadProfilePictureDir;

    @Value("${app.upload.docs.dir}")
    private String uploadDocsDir;

    @Operation(summary = "Find all associates")
    @GetMapping
    public Page<AssociateResponseDto> findAllAssociate(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) Long unionCard,
            Pageable pageable
    ) {
        return associateService.findAll(name, cpf, unionCard, pageable);
    }

    @Operation(summary = "Find associate by id")
    @GetMapping(path = "/{id}")
    public AssociateResponseDto findAssociateById(@PathVariable Long id) {
        return associateService.findById(id);
    }

    @Operation(summary = "Find all birthday associates")
    @GetMapping(path = "/birthdays")
    public Page<AssociateResponseDto> findAllBirthdayAssociates(
            Pageable pageable,
            @RequestParam(required = false, defaultValue = "DAY") PeriodEnum period
    ) {
        return associateService.findAllBirthdayAssociates(pageable, period);
    }

    @Operation(summary = "Save associate")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AssociateResponseDto save(@RequestBody @Valid AssociatePostDto dto) {
        return associateService.save(dto);
    }

    @Operation(summary = "Update associate")
    @PutMapping(path = "/{id}")
    public AssociateResponseDto update(
            @PathVariable Long id,
            @RequestBody @Valid AssociatePutDto dto
    ) {
        return associateService.update(id, dto);
    }

    @Operation(summary = "Find associate photo by associate id")
    @GetMapping(path = "/{associateId}/profile-picture")
    public FileResponseDto findPhoto(@PathVariable Long associateId) {
        return associatePhotoService.findByAssociateId(associateId);
    }

    @Operation(summary = "Find associate document by associate id")
    @GetMapping(path = "/{associateId}/documents")
    public Page<FileResponseDto> findDocuments(@PathVariable Long associateId, Pageable pageable) {
        return associatePhotoService.findDocumentsByAssociateId(associateId, pageable);
    }

    @Operation(summary = "Find associate document by associate id and document id")
    @GetMapping(path = "/{associateId}/documents/{documentId}")
    public FileResponseDto findDocument(
            @PathVariable Long associateId,
            @PathVariable Long documentId
    ) {
        return associatePhotoService.findDocumentByAssociateIdAndDocumentId(associateId, documentId);
    }

    @Operation(summary = "Upload associate photo")
    @PostMapping(path = "/{associateId}/profile-picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public FileResponseDto uploadPhoto(
            @PathVariable Long associateId,
            @RequestParam("file") MultipartFile file
    ) {
        return associatePhotoService.save(associateId, file, uploadProfilePictureDir);
    }

    @Operation(summary = "Upload associate document")
    @PostMapping(path = "/{associateId}/documents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public FileResponseDto uploadDocument(
            @PathVariable Long associateId,
            @RequestParam("file") MultipartFile file
    ) {
        return associatePhotoService.saveDocument(associateId, file, uploadDocsDir);
    }

    @Operation(summary = "Delete associate")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        associateService.delete(id);
    }

    @Operation(summary = "Delete associate photo")
    @DeleteMapping(path = "/{associateId}/profile-picture")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePhoto(@PathVariable Long associateId) {
        associatePhotoService.delete(associateId, uploadProfilePictureDir);
    }

    @Operation(summary = "Delete associate document")
    @DeleteMapping(path = "/{associateId}/documents/{documentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDocument(
            @PathVariable Long associateId,
            @PathVariable Long documentId
    ) {
        associatePhotoService.deleteDocument(associateId, documentId, uploadDocsDir);
    }

    @Operation(summary = "Export associate to pdf")
    @GetMapping(path = "/{id}/export/pdf", produces = "application/pdf")
    public byte[] exportAssociateToPdf(
            @PathVariable Long id,
            HttpServletResponse response
    ) {
        return associateService.exportAssociateToPdf(id, response);
    }

    @Operation(summary = "Export associate membership card to pdf")
    @GetMapping(path = "/{id}/membership-card/export/pdf", produces = "application/pdf")
    public byte[] exportAssociateMembershipCardToPdf(
            @PathVariable Long id,
            HttpServletResponse response
    ) {
        return associateService.exportAssociateMembershipCardToPdf(id, response);
    }
}
