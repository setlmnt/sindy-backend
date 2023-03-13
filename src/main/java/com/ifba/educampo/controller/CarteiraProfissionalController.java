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

import com.ifba.educampo.domain.CarteiraProfissional;
import com.ifba.educampo.requests.CarteiraProfissionalPostRequestBody;
import com.ifba.educampo.requests.CarteiraProfissionalPutRequestBody;
import com.ifba.educampo.service.CarteiraProfissionalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/carteira")
@RequiredArgsConstructor
public class CarteiraProfissionalController {
	@Autowired
	private CarteiraProfissionalService carteiraService;
	
	@GetMapping
	public ResponseEntity<List<CarteiraProfissional>> list(){
		return ResponseEntity.ok(carteiraService.listAll());
	}
	
	@GetMapping(path = "/{id}")
    public ResponseEntity<CarteiraProfissional> findById(@PathVariable long id){
        return ResponseEntity.ok(carteiraService.findCarteira(id));
    }
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id){
		carteiraService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping
	public ResponseEntity<CarteiraProfissional> save(@RequestBody CarteiraProfissionalPostRequestBody carteiraPostRequestBody){
		return new ResponseEntity<>(carteiraService.save(carteiraPostRequestBody), HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<Void> replace(@RequestBody CarteiraProfissionalPutRequestBody carteiraPutRequestBody){
		carteiraService.replace(carteiraPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<Void> updateFields(@PathVariable long id, Map<String, Object> fields){
		carteiraService.updateByFields(id, fields);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
