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

import com.ifba.educampo.domain.Associado;
import com.ifba.educampo.domain.CarteiraProfissional;
import com.ifba.educampo.domain.Dependentes;
import com.ifba.educampo.domain.Endereco;
import com.ifba.educampo.domain.Filiacao;
import com.ifba.educampo.domain.FotoAssociado;
import com.ifba.educampo.domain.Naturalidade;
import com.ifba.educampo.requests.AssociadoPostRequestBody;
import com.ifba.educampo.requests.AssociadoPutRequestBody;
import com.ifba.educampo.requests.CarteiraProfissionalPostRequestBody;
import com.ifba.educampo.requests.CarteiraProfissionalPutRequestBody;
import com.ifba.educampo.requests.DependentesPostRequestBody;
import com.ifba.educampo.requests.DependentesPutRequestBody;
import com.ifba.educampo.requests.EnderecoPostRequestBody;
import com.ifba.educampo.requests.EnderecoPutRequestBody;
import com.ifba.educampo.requests.FiliacaoPostRequestBody;
import com.ifba.educampo.requests.FiliacaoPutRequestBody;
import com.ifba.educampo.requests.FotoAssociadoPostRequestBody;
import com.ifba.educampo.requests.FotoAssociadoPutRequestBody;
import com.ifba.educampo.requests.NaturalidadePostRequestBody;
import com.ifba.educampo.requests.NaturalidadePutRequestBody;
import com.ifba.educampo.service.AssociadoService;
import com.ifba.educampo.service.CarteiraProfissionalService;
import com.ifba.educampo.service.DependentesService;
import com.ifba.educampo.service.EnderecoService;
import com.ifba.educampo.service.FiliacaoService;
import com.ifba.educampo.service.FotoAssociadoService;
import com.ifba.educampo.service.NaturalidadeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/associados")
@RequiredArgsConstructor
public class AssociadoController {
	@Autowired
	private AssociadoService associadoService;
	
	@GetMapping(path = "/associados")
	public ResponseEntity<List<Associado>> listAssociado(){
		return ResponseEntity.ok(associadoService.listAll());
	}
	
	@GetMapping(path = "/associados/{id}")
    public ResponseEntity<Associado> findAssociadoById(@PathVariable long id){
        return ResponseEntity.ok(associadoService.findAssociado(id));
    }
	
	@GetMapping(path = "associados/nome/{name}")
    public ResponseEntity<Associado> findAssociadoByName(@PathVariable String name){
        return ResponseEntity.ok(associadoService.findAssociadoByName(name));
    }
	
	@GetMapping(path = "associados/cpf/{cpf}")
    public ResponseEntity<Associado> findAssociadoByCpf(@PathVariable String cpf){
        return ResponseEntity.ok(associadoService.findAssociadoByCpf(cpf));
    }
	
	@GetMapping(path = "associados/carteira/{carteira}")
    public ResponseEntity<Associado> findAssociadoByCarteiraSindical(@PathVariable Long carteira){
        return ResponseEntity.ok(associadoService.findAssociadoByCarteiraSindical(carteira));
    }
	
	@DeleteMapping(path = "/deleteAssociados/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id){
		associadoService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping(path = "/postAssociados")
	public ResponseEntity<Associado> save(@RequestBody AssociadoPostRequestBody associadoPostRequestBody){
		return new ResponseEntity<>(associadoService.save(associadoPostRequestBody), HttpStatus.CREATED);
	}
	
	@PutMapping(path = "/putAssociados")
	public ResponseEntity<Void> replace(@RequestBody AssociadoPutRequestBody associadoPutRequestBody){
		associadoService.replace(associadoPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PatchMapping("/patchAssociados/{id}")
	public ResponseEntity<Void> updateFields(@PathVariable long id, Map<String, Object> fields){
		associadoService.updateByFields(id, fields);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
