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

import com.ifba.educampo.domain.Endereco;
import com.ifba.educampo.requests.EnderecoPostRequestBody;
import com.ifba.educampo.requests.EnderecoPutRequestBody;
import com.ifba.educampo.service.EnderecoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/Enderecos")
@RequiredArgsConstructor
public class EnderecoController {
	@Autowired
	private EnderecoService enderecoService;
	
	@GetMapping
	public ResponseEntity<List<Endereco>> list(){
		return ResponseEntity.ok(enderecoService.listAll());
	}
	
	@GetMapping(path = "/{id}")
    public ResponseEntity<Endereco> findById(@PathVariable long id){
        return ResponseEntity.ok(enderecoService.findEndereco(id));
    }
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id){
		enderecoService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping
	public ResponseEntity<Endereco> save(@RequestBody EnderecoPostRequestBody enderecoPostRequestBody){
		return new ResponseEntity<>(enderecoService.save(enderecoPostRequestBody), HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<Void> replace(@RequestBody EnderecoPutRequestBody enderecoPutRequestBody){
		enderecoService.replace(enderecoPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<Void> updateFields(@PathVariable long id, Map<String, Object> fields){
		enderecoService.updateByFields(id, fields);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
