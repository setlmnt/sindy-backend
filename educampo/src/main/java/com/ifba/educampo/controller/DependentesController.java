package com.ifba.educampo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifba.educampo.domain.Dependentes;
import com.ifba.educampo.requests.DependentesPostRequestBody;
import com.ifba.educampo.requests.DependentesPutRequestBody;
import com.ifba.educampo.service.DependentesService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/Dependentess")
@RequiredArgsConstructor
public class DependentesController {
	@Autowired
	private DependentesService dependentesService;
	
	@GetMapping
	public ResponseEntity<List<Dependentes>> list(){
		return ResponseEntity.ok(dependentesService.listAll());
	}
	
	@GetMapping(path = "/{id}")
    public ResponseEntity<Dependentes> findById(@PathVariable long id){
        return ResponseEntity.ok(dependentesService.findDependente(id));
    }
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id){
		dependentesService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping
	public ResponseEntity<Dependentes> save(@RequestBody DependentesPostRequestBody DependentesPostRequestBody){
		return new ResponseEntity<>(dependentesService.save(DependentesPostRequestBody), HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<Void> replace(@RequestBody DependentesPutRequestBody dependentesPutRequestBody){
		dependentesService.replace(dependentesPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
