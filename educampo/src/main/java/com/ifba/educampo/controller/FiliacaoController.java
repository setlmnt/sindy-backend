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

import com.ifba.educampo.domain.Filiacao;
import com.ifba.educampo.requests.FiliacaoPostRequestBody;
import com.ifba.educampo.requests.FiliacaoPutRequestBody;
import com.ifba.educampo.service.FiliacaoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/Filiacaos")
@RequiredArgsConstructor
public class FiliacaoController {
	@Autowired
	private FiliacaoService filiacaoService;
	
	@GetMapping
	public ResponseEntity<List<Filiacao>> list(){
		return ResponseEntity.ok(filiacaoService.listAll());
	}
	
	@GetMapping(path = "/{id}")
    public ResponseEntity<Filiacao> findById(@PathVariable long id){
        return ResponseEntity.ok(filiacaoService.findFiliacao(id));
    }
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id){
		filiacaoService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping
	public ResponseEntity<Filiacao> save(@RequestBody FiliacaoPostRequestBody filiacaoPostRequestBody){
		return new ResponseEntity<>(filiacaoService.save(filiacaoPostRequestBody), HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<Void> replace(@RequestBody FiliacaoPutRequestBody filiacaoPutRequestBody){
		filiacaoService.replace(filiacaoPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
