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

import com.ifba.educampo.domain.FotoAssociado;
import com.ifba.educampo.requests.FotoAssociadoPostRequestBody;
import com.ifba.educampo.requests.FotoAssociadoPutRequestBody;
import com.ifba.educampo.service.FotoAssociadoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/FotoAssociados")
@RequiredArgsConstructor
public class FotoAssociadoController {
	@Autowired
	private FotoAssociadoService fotoAssociadoService;
	
	@GetMapping
	public ResponseEntity<List<FotoAssociado>> list(){
		return ResponseEntity.ok(fotoAssociadoService.listAll());
	}
	
	@GetMapping(path = "/{id}")
    public ResponseEntity<FotoAssociado> findById(@PathVariable long id){
        return ResponseEntity.ok(fotoAssociadoService.findFotoAssociado(id));
    }
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id){
		fotoAssociadoService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping
	public ResponseEntity<FotoAssociado> save(@RequestBody FotoAssociadoPostRequestBody fotoAssociadoPostRequestBody){
		return new ResponseEntity<>(fotoAssociadoService.save(fotoAssociadoPostRequestBody), HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<Void> replace(@RequestBody FotoAssociadoPutRequestBody fotoAssociadoPutRequestBody){
		fotoAssociadoService.replace(fotoAssociadoPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
