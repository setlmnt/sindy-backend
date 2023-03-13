package com.ifba.educampo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.ifba.educampo.domain.Naturalidade;
import com.ifba.educampo.requests.NaturalidadePostRequestBody;
import com.ifba.educampo.requests.NaturalidadePutRequestBody;
import com.ifba.educampo.service.NaturalidadeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/Naturalidades")
@RequiredArgsConstructor
public class NaturalidadeController {
	@Autowired
	private NaturalidadeService naturalidadeService;
	
	@GetMapping
	public ResponseEntity<List<Naturalidade>> list(){
		return ResponseEntity.ok(naturalidadeService.listAll());
	}
	
	@GetMapping(path = "/{id}")
    public ResponseEntity<Naturalidade> findById(@PathVariable long id){
        return ResponseEntity.ok(naturalidadeService.findNaturalidade(id));
    }
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id){
		naturalidadeService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping
	public ResponseEntity<Naturalidade> save(@RequestBody NaturalidadePostRequestBody naturalidadePostRequestBody){
		return new ResponseEntity<>(naturalidadeService.save(naturalidadePostRequestBody), HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<Void> replace(@RequestBody NaturalidadePutRequestBody naturalidadePutRequestBody){
		naturalidadeService.replace(naturalidadePutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<Void> updateFields(@PathVariable long id, Map<String, Object> fields){
		naturalidadeService.updateByFields(id, fields);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
