package br.com.sindy.api.controller;

import br.com.sindy.core.annotation.FileSize;
import br.com.sindy.core.annotation.FileType;
import br.com.sindy.domain.dto.FileResponseDto;
import br.com.sindy.domain.dto.associate.AssociatePostDto;
import br.com.sindy.domain.dto.associate.AssociatePutDto;
import br.com.sindy.domain.dto.associate.AssociateResponseDto;
import br.com.sindy.domain.enums.PeriodEnum;
import br.com.sindy.domain.service.AssociateFileService;
import br.com.sindy.domain.service.AssociateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
@RequiredArgsConstructor
public class AssociatesController {
    public static final String ASSOCIATES = "associates";
    public static final String ASSOCIATE = "associate";
    public static final String BIRTHDAYS = "birthdays";
    public static final String LOCAL_OFFICE_ASSOCIATES = "local-office-associates";
    public static final String PROFILE_PICTURE = "profile-picture";
    public static final String DOCUMENTS = "documents";
    public static final String DOCUMENT = "document";

    private final AssociateService associateService;
    private final AssociateFileService associateFileService;

    @Operation(summary = "Find all associates")
    @GetMapping
    @Cacheable(value = ASSOCIATES)
    public Page<AssociateResponseDto> findAllAssociate(
            @Parameter(description = "Search by name, cpf or union card")
            @RequestParam(required = false) String query,
            Pageable pageable
    ) {
        return associateService.findAll(query, pageable);
    }

    @Operation(summary = "Find associate by id")
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Cacheable(value = ASSOCIATE, key = "#id")
    public AssociateResponseDto findAssociateById(@PathVariable Long id) {
        System.out.println("findAssociateById");
        return associateService.findById(id);
    }

    @Operation(summary = "Export associate to pdf")
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] exportAssociateToPdf(
            @PathVariable Long id,
            HttpServletResponse response
    ) {
        return associateService.exportAssociateToPdf(id, response);
    }

    @Operation(summary = "Export associate membership card to pdf")
    @GetMapping(path = "/{id}/membership-card", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] exportAssociateMembershipCardToPdf(
            @PathVariable Long id,
            HttpServletResponse response
    ) {
        return associateService.exportAssociateMembershipCardToPdf(id, response);
    }

    @Operation(summary = "Find all birthday associates")
    @GetMapping(path = "/birthdays")
    @Cacheable(value = BIRTHDAYS, key = "#period")
    public Page<AssociateResponseDto> findAllBirthdayAssociates(
            Pageable pageable,
            @RequestParam(required = false, defaultValue = "DAY") PeriodEnum period
    ) {
        return associateService.findAllBirthdayAssociates(pageable, period);
    }

    @Operation(summary = "Save associate")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(value = {ASSOCIATES, BIRTHDAYS, LOCAL_OFFICE_ASSOCIATES}, allEntries = true)
    public AssociateResponseDto save(@RequestBody @Valid AssociatePostDto dto) {
        return associateService.save(dto);
    }

    @Operation(summary = "Update associate")
    @PutMapping(path = "/{id}")
    @CacheEvict(value = {ASSOCIATES, ASSOCIATE, BIRTHDAYS}, allEntries = true)
    public AssociateResponseDto update(
            @PathVariable Long id,
            @RequestBody @Valid AssociatePutDto dto
    ) {
        return associateService.update(id, dto);
    }

    @Operation(summary = "Delete associate")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = {ASSOCIATES, ASSOCIATE, BIRTHDAYS}, allEntries = true)
    public void delete(@PathVariable Long id) {
        associateService.delete(id);
    }

    @Operation(summary = "Find associate photo by associate id")
    @GetMapping(path = "/{associateId}/profile-picture")
    @Cacheable(value = PROFILE_PICTURE, key = "#associateId")
    public FileResponseDto findPhoto(@PathVariable Long associateId) {
        return associateFileService.findByAssociateId(associateId);
    }

    @Operation(summary = "Upload associate photo")
    @PostMapping(path = "/{associateId}/profile-picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(value = {ASSOCIATES, ASSOCIATE, BIRTHDAYS, PROFILE_PICTURE}, allEntries = true)
    public FileResponseDto uploadPhoto(
            @PathVariable Long associateId,
            @RequestParam("file") @FileSize @FileType(type = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE}) MultipartFile file
    ) {
        return associateFileService.saveProfilePicture(associateId, file);
    }

    @Operation(summary = "Delete associate photo")
    @DeleteMapping(path = "/{associateId}/profile-picture")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = {ASSOCIATES, ASSOCIATE, BIRTHDAYS, PROFILE_PICTURE}, allEntries = true)
    public void deletePhoto(@PathVariable Long associateId) {
        associateFileService.deleteProfilePicture(associateId);
    }

    @Operation(summary = "Find associate document by associate id")
    @GetMapping(path = "/{associateId}/documents")
    @Cacheable(value = DOCUMENTS, key = "#associateId")
    public Page<FileResponseDto> findDocuments(@PathVariable Long associateId, Pageable pageable) {
        return associateFileService.findByAssociateId(associateId, pageable);
    }

    @Operation(summary = "Find associate document by associate id and document id")
    @GetMapping(path = "/{associateId}/documents/{documentId}")
    @Cacheable(value = DOCUMENT, key = "#documentId")
    public FileResponseDto findDocument(
            @PathVariable Long associateId,
            @PathVariable Long documentId
    ) {
        return associateFileService.findByAssociateIdAndDocumentId(associateId, documentId);
    }

    @Operation(summary = "Upload associate document")
    @PostMapping(path = "/{associateId}/documents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(value = DOCUMENTS, allEntries = true)
    public FileResponseDto uploadDocument(
            @PathVariable Long associateId,
            @RequestParam("file") @FileSize(max = "5MB") @FileType(type = MediaType.APPLICATION_PDF_VALUE) MultipartFile file
    ) {
        return associateFileService.saveDocument(associateId, file);
    }

    @Operation(summary = "Delete associate document")
    @DeleteMapping(path = "/{associateId}/documents/{documentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = {DOCUMENTS, DOCUMENT}, allEntries = true)
    public void deleteDocument(
            @PathVariable Long associateId,
            @PathVariable Long documentId
    ) {
        associateFileService.deleteDocument(associateId, documentId);
    }
}
