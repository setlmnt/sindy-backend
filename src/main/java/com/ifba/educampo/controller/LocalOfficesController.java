package com.ifba.educampo.controller;

import com.ifba.educampo.model.dto.LocalOfficeDto;
import com.ifba.educampo.model.entity.Associate;
import com.ifba.educampo.model.entity.LocalOffice;
import com.ifba.educampo.service.LocalOfficeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/local-offices")
@RequiredArgsConstructor
public class LocalOfficesController { // Classe de controle para as Delegacias (Escritório Local)
    private final LocalOfficeService localOfficeService;

    @GetMapping
    public ResponseEntity<Page<LocalOffice>> listLocalOffices(Pageable pageable) {
        return ResponseEntity.ok(localOfficeService.listAll(pageable));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<LocalOffice> findLocalOfficeById(@PathVariable long id) {
        return ResponseEntity.ok(localOfficeService.findLocalOffice(id));
    }

    @GetMapping(path = "/{id}/associates")
    public ResponseEntity<Page<Associate>> findAssociatesByLocalOfficeId(@PathVariable long id, Pageable pageable) {
        return ResponseEntity.ok(localOfficeService.listAllAssociates(id, pageable));
    }

    @PostMapping
    public ResponseEntity<LocalOffice> save(@RequestBody @Valid LocalOfficeDto localOfficeDto) {
        return ResponseEntity.ok(localOfficeService.save(localOfficeDto));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        localOfficeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody @Valid LocalOfficeDto localOfficeDto) {
        localOfficeService.replace(localOfficeDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
