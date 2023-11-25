package com.ifba.educampo.controller;

import com.ifba.educampo.dto.ImageResponseDto;
import com.ifba.educampo.dto.associate.AssociatePostDto;
import com.ifba.educampo.dto.associate.AssociatePutDto;
import com.ifba.educampo.dto.associate.AssociateResponseDto;
import com.ifba.educampo.exception.NotFoundException;
import com.ifba.educampo.mapper.associate.AssociateMapper;
import com.ifba.educampo.model.entity.associate.Associate;
import com.ifba.educampo.model.enums.MaritalStatus;
import com.ifba.educampo.service.PdfService;
import com.ifba.educampo.service.associate.AssociatePhotoService;
import com.ifba.educampo.service.associate.AssociateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.activation.MimetypesFileTypeMap;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;

@Tag(name = "Associates", description = "Associates API")
@RestController
@RequestMapping(value = "/api/v1/associates")
@RequiredArgsConstructor
public class AssociatesController { // Classe de controle para o Associado
    private final AssociateService associateService;
    private final AssociatePhotoService associatePhotoService;
    private final AssociateMapper associateMapper;
    private final PdfService pdfService;

    @Operation(summary = "Find all associates")
    @GetMapping
    public Page<AssociateResponseDto> listAssociate(
            @RequestParam(required = false) String query,
            Pageable pageable
    ) {
        // TODO: Melhorar a busca por query
        if (query == null) return associateService.listAll(pageable);
        return associateService.findAssociateByNameOrCpfOrUnionCard(query, pageable);
    }

    @Operation(summary = "Find associate by id")
    @GetMapping(path = "/{id}")
    public AssociateResponseDto findAssociateById(@PathVariable long id) {
        return associateService.findAssociate(id);
    }

    @Operation(summary = "Find all associates photos")
    @GetMapping(path = "/photos")
    public Page<ImageResponseDto> listAssociatePhoto(Pageable pageable) {
        return associatePhotoService.listAll(pageable);
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

    @Operation(summary = "Load associate photo")
    @GetMapping(path = "/photos/{photoName}")
    public ResponseEntity<?> loadPhoto(@PathVariable String photoName) {
        Resource resource = associatePhotoService.load(photoName);

        if (resource == null) throw new NotFoundException("Photo not found");

        // Pegar o tipo de conteúdo do arquivo
        MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
        String contentType = mimeTypesMap.getContentType(resource.getFilename());

        // Adicionar o tipo de conteúdo do arquivo no cabeçalho da resposta
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.parseMediaType(contentType).toString());

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @Operation(summary = "Find associate photo by associate id")
    @GetMapping(path = "/{associateId}/photos")
    public ImageResponseDto findPhoto(@PathVariable Long associateId) {
        return associatePhotoService.findByAssociateId(associateId);
    }

    @Operation(summary = "Upload associate photo")
    @PostMapping(path = "/{associateId}/photos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ImageResponseDto uploadPhoto(
            @PathVariable long associateId,
            @RequestParam("file") MultipartFile file
    ) {
        return associatePhotoService.save(associateId, file);
    }

    @Operation(summary = "Delete associate")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        associateService.delete(id);
    }

    @Operation(summary = "Delete associate photo")
    @DeleteMapping(path = "/{associateId}/photos")
    public void deletePhoto(@PathVariable Long associateId) {
        associatePhotoService.delete(associateId);
    }

    @Operation(summary = "Export associate to pdf")
    @GetMapping(path = "/{id}/export/pdf", produces = "application/pdf")
    public byte[] exportAssociateToPdf(
            @PathVariable Long id,
            HttpServletResponse response
    ) {
        AssociateResponseDto associateResponseDto = associateService.findAssociate(id);
        Associate associate = associateMapper.responseDtoToEntity(associateResponseDto);

        if (associate == null) return null;

        response.setContentType("application/pdf");
        response.setHeader(
                "Content-Disposition",
                "attachment; filename=" + associate.getName() + "-" + associate.getUnionCard() + ".pdf"
        );

        Context context = new Context();
        context.setVariable("associate", associate);
        context.setVariable("divorced", MaritalStatus.DIVORCED);
        context.setVariable("never_married", MaritalStatus.NEVER_MARRIED);
        context.setVariable("married", MaritalStatus.MARRIED);
        context.setVariable("widowed", MaritalStatus.WIDOWED);

        return pdfService.generatePdfByTemplate("associate", context);
    }
}
