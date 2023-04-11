package com.ifba.educampo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifba.educampo.domain.Associado;
import com.ifba.educampo.requests.AssociadoPostRequestBody;
import com.ifba.educampo.requests.AssociadoPutRequestBody;
import com.ifba.educampo.service.AssociadoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/associados")
@RequiredArgsConstructor
public class AssociadoController {
	@Autowired
	private AssociadoService associadoService;
	
	@GetMapping
	public ResponseEntity<Page<Associado>> listAssociado(Pageable pageable){
		return ResponseEntity.ok(associadoService.listAll(pageable));
	}
	
	@GetMapping(path = "/{id}")
    public ResponseEntity<Associado> findAssociadoById(@PathVariable long id){
        return ResponseEntity.ok(associadoService.findAssociado(id));
    }
	
	@GetMapping(path = "/nome/{name}")
    public ResponseEntity<Page<Associado>> findAssociadoByName(@PathVariable String name, Pageable pageable){
        return ResponseEntity.ok(associadoService.findAssociadoByName(name, pageable));
    }
	
	@GetMapping(path = "/cpf/{cpf}")
    public ResponseEntity<Page<Associado>> findAssociadoByCpf(@PathVariable String cpf, Pageable pageable){
        return ResponseEntity.ok(associadoService.findAssociadoByCpf(cpf, pageable));
    }
	
	@GetMapping(path = "/carteira/{carteira}")
    public ResponseEntity<Page<Associado>> findAssociadoByCarteiraSindical(@PathVariable Long carteira, Pageable pageable){
        return ResponseEntity.ok(associadoService.findAssociadoByCarteiraSindical(carteira, pageable));
    }
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id){
		associadoService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping
	public ResponseEntity<Associado> save(@RequestBody AssociadoPostRequestBody associadoPostRequestBody){
		return new ResponseEntity<>(associadoService.save(associadoPostRequestBody), HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<Void> replace(@RequestBody AssociadoPutRequestBody associadoPutRequestBody){
		associadoService.replace(associadoPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PatchMapping
	public ResponseEntity<Void> updateFields(@PathVariable long id, Map<String, Object> fields){
		associadoService.updateByFields(id, fields);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
