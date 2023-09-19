package com.ifba.educampo.controller;

import com.ifba.educampo.exception.AssociatePhotoNotFoundException;
import com.ifba.educampo.model.dto.AssociateDto;
import com.ifba.educampo.model.entity.Associate;
import com.ifba.educampo.model.entity.AssociatePhoto;
import com.ifba.educampo.model.enums.MaritalStatus;
import com.ifba.educampo.service.AssociatePhotoService;
import com.ifba.educampo.service.AssociateService;
import com.ifba.educampo.service.PdfService;
import jakarta.activation.MimetypesFileTypeMap;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
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

@RestController
@RequestMapping(value = "/associates")
@RequiredArgsConstructor
public class AssociatesController { // Classe de controle para o Associado
    private final AssociateService associateService;
    private final AssociatePhotoService associatePhotoService;
    private final PdfService pdfService;

    @GetMapping
    public ResponseEntity<Page<Associate>> listAssociate(
            @RequestParam(required = false) String query,
            Pageable pageable
    ) {
        if (query == null) return ResponseEntity.ok(associateService.listAll(pageable));
        return ResponseEntity.ok(associateService.findAssociateByNameOrCpfOrUnionCard(query, pageable));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Associate> findAssociateById(@PathVariable long id) {
        return ResponseEntity.ok(associateService.findAssociate(id));
    }

    @DeleteMapping(path = "/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable long id) {
        associateService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<Associate> save(@RequestBody @Valid AssociateDto associateDto) {
        return new ResponseEntity<>(associateService.save(associateDto), HttpStatus.CREATED);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Void> replace(@RequestBody @Valid AssociateDto associateDto) {
        associateService.replace(associateDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/photos")
    public ResponseEntity<Page<AssociatePhoto>> listAssociatePhoto(Pageable pageable) {
        return ResponseEntity.ok(associatePhotoService.listAll(pageable));
    }

    @GetMapping(path = "/photos/{photoName}")
    public ResponseEntity<?> loadPhoto(@PathVariable String photoName) {
        Resource resource = associatePhotoService.load(photoName);

        if (resource == null) throw new AssociatePhotoNotFoundException("Photo not found");

        // Pegar o tipo de conteúdo do arquivo
        MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
        String contentType = mimeTypesMap.getContentType(resource.getFilename());

        // Adicionar o tipo de conteúdo do arquivo no cabeçalho da resposta
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.parseMediaType(contentType).toString());

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @GetMapping(path = "/{associateId}/photos")
    public ResponseEntity<AssociatePhoto> findPhoto(@PathVariable long associateId) {
        return ResponseEntity.ok(associatePhotoService.findAssociatePhotoByAssociateId(associateId));
    }

    @PostMapping(path = "/{associateId}/photos")
    public ResponseEntity<AssociatePhoto> uploadPhoto(@PathVariable long associateId, @RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(associatePhotoService.uploadAndSaveAssociatePhoto(associateId, file), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{associateId}/photos")
    public ResponseEntity<Void> deletePhoto(@PathVariable long associateId) {
        associatePhotoService.deleteAssociatePhoto(associateId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/{id}/export/pdf", produces = "application/pdf")
    public byte[] exportAssociateToPdf(
            @PathVariable long id,
            HttpServletResponse response
    ) {
        Associate associate = associateService.findAssociate(id);

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
