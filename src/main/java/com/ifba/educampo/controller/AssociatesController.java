package com.ifba.educampo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ifba.educampo.domain.Associate;
import com.ifba.educampo.requests.AssociatePostRequestBody;
import com.ifba.educampo.requests.AssociatePutRequestBody;
import com.ifba.educampo.service.AssociateService;

import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@RestController
@RequestMapping(value = "/associates")
@RequiredArgsConstructor
public class AssociatesController { // Classe de controle para o Associado
	private final AssociateService associateService;
	
	@GetMapping
	public ResponseEntity<Page<Associate>> listAssociate(@RequestParam(required = false) String query, Pageable pageable){
		if (query == null) return ResponseEntity.ok(associateService.listAll(pageable));
		return ResponseEntity.ok(associateService.findAssociateByNameOrCpfOrUnionCard(query, pageable));
	}
	
	@GetMapping(path = "/{id}")
    public ResponseEntity<Associate> findAssociateById(@PathVariable long id){
        return ResponseEntity.ok(associateService.findAssociate(id));
    }
	
	@DeleteMapping(path = "/{id}")
	@Transactional
	public ResponseEntity<Void> delete(@PathVariable long id){
		associateService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping
	public ResponseEntity<Associate> save(@RequestBody AssociatePostRequestBody associatePostRequestBody){
		return new ResponseEntity<>(associateService.save(associatePostRequestBody), HttpStatus.CREATED);
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<Void> replace(@RequestBody AssociatePutRequestBody associatePutRequestBody){
		associateService.replace(associatePutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PatchMapping
	@Transactional
	public ResponseEntity<Void> updateFields(@PathVariable long id, Map<String, Object> fields){
		associateService.updateByFields(id, fields);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
