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
	@Autowired
	private CarteiraProfissionalService carteiraService;
	@Autowired
	private DependentesService dependentesService;
	@Autowired
	private EnderecoService enderecoService;
	@Autowired
	private FiliacaoService filiacaoService;
	@Autowired
	private FotoAssociadoService fotoAssociadoService;
	@Autowired
	private NaturalidadeService naturalidadeService;
	
	@GetMapping(path = "/associados")
	public ResponseEntity<List<Associado>> listAssociado(){
		return ResponseEntity.ok(associadoService.listAll());
	}
	
	@GetMapping(path = "/associado/{id}")
    public ResponseEntity<Associado> findAssociadoById(@PathVariable long id){
        return ResponseEntity.ok(associadoService.findAssociado(id));
    }
	
	@GetMapping(path = "associado/nome/{name}")
    public ResponseEntity<Associado> findAssociadoByName(@PathVariable String name){
        return ResponseEntity.ok(associadoService.findAssociadoByName(name));
    }
	
	@GetMapping(path = "associado/cpf/{cpf}")
    public ResponseEntity<Associado> findAssociadoByCpf(@PathVariable String cpf){
        return ResponseEntity.ok(associadoService.findAssociadoByCpf(cpf));
    }
	
	@GetMapping(path = "associado/carteira/{carteira}")
    public ResponseEntity<Associado> findAssociadoByCarteiraSindical(@PathVariable Long carteira){
        return ResponseEntity.ok(associadoService.findAssociadoByCarteiraSindical(carteira));
    }
	
	@DeleteMapping(path = "/deleteAssociado/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id){
		associadoService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping(path = "/postAssociado")
	public ResponseEntity<Associado> save(@RequestBody AssociadoPostRequestBody associadoPostRequestBody){
		return new ResponseEntity<>(associadoService.save(associadoPostRequestBody), HttpStatus.CREATED);
	}
	
	@PutMapping(path = "/putAssociado")
	public ResponseEntity<Void> replace(@RequestBody AssociadoPutRequestBody associadoPutRequestBody){
		associadoService.replace(associadoPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PatchMapping("/patchAssociado/{id}")
	public ResponseEntity<Void> updateFields(@PathVariable long id, Map<String, Object> fields){
		associadoService.updateByFields(id, fields);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	@GetMapping(path = "/carteirasProfissionais")
	public ResponseEntity<List<CarteiraProfissional>> listCarteira(){
		return ResponseEntity.ok(carteiraService.listAll());
	}
	
	@GetMapping(path = "/carteiraProfissional/{id}")
    public ResponseEntity<CarteiraProfissional> findCarteiraProfissionalById(@PathVariable long id){
        return ResponseEntity.ok(carteiraService.findCarteira(id));
    }
	
	@DeleteMapping(path = "/deleteCarteiraProfissional/{id}")
	public ResponseEntity<Void> deleteCarteiraProfissional(@PathVariable long id){
		carteiraService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping(path = "/postCarteiraProfissional")
	public ResponseEntity<CarteiraProfissional> saveCarteiraProfissional(@RequestBody CarteiraProfissionalPostRequestBody carteiraPostRequestBody){
		return new ResponseEntity<>(carteiraService.save(carteiraPostRequestBody), HttpStatus.CREATED);
	}
	
	@PutMapping(path = "/putCarteiraProfissional")
	public ResponseEntity<Void> replaceCarteiraProfissional(@RequestBody CarteiraProfissionalPutRequestBody carteiraPutRequestBody){
		carteiraService.replace(carteiraPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PatchMapping("/patchCarteiraProfissional/{id}")
	public ResponseEntity<Void> updateFieldsCarteiraProfissional(@PathVariable long id, Map<String, Object> fields){
		carteiraService.updateByFields(id, fields);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping(path = "/dependentes")
	public ResponseEntity<List<Dependentes>> listDependentes(){
		return ResponseEntity.ok(dependentesService.listAll());
	}
	
	@GetMapping(path = "/dependente/{id}")
    public ResponseEntity<Dependentes> findDependenteById(@PathVariable long id){
        return ResponseEntity.ok(dependentesService.findDependente(id));
    }
	
	@DeleteMapping(path = "/deleteDependente/{id}")
	public ResponseEntity<Void> deleteDependente(@PathVariable long id){
		dependentesService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping(path = "/postDependente")
	public ResponseEntity<Dependentes> saveDependentes(@RequestBody DependentesPostRequestBody dependentesPostRequestBody){
		return new ResponseEntity<>(dependentesService.save(dependentesPostRequestBody), HttpStatus.CREATED);
	}
	
	@PutMapping(path = "/putDependente")
	public ResponseEntity<Void> replaceDependentes(@RequestBody DependentesPutRequestBody dependentesPutRequestBody){
		dependentesService.replace(dependentesPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PatchMapping("/patchDependente/{id}")
	public ResponseEntity<Void> updateFieldsDependentes(@PathVariable long id, Map<String, Object> fields){
		dependentesService.updateByFields(id, fields);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping(path = "/enderecos")
	public ResponseEntity<List<Endereco>> listEnderecos(){
		return ResponseEntity.ok(enderecoService.listAll());
	}
	
	@GetMapping(path = "/endereco/{id}")
    public ResponseEntity<Endereco> findEnderecoById(@PathVariable long id){
        return ResponseEntity.ok(enderecoService.findEndereco(id));
    }
	
	@DeleteMapping(path = "/deleteEndereco/{id}")
	public ResponseEntity<Void> deleteEndereco(@PathVariable long id){
		enderecoService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping(path = "/postEndereco")
	public ResponseEntity<Endereco> saveEndereco(@RequestBody EnderecoPostRequestBody enderecoPostRequestBody){
		return new ResponseEntity<>(enderecoService.save(enderecoPostRequestBody), HttpStatus.CREATED);
	}
	
	@PutMapping(path = "/putEndereco")
	public ResponseEntity<Void> replaceEndereco(@RequestBody EnderecoPutRequestBody enderecoPutRequestBody){
		enderecoService.replace(enderecoPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PatchMapping("/patchEndereco/{id}")
	public ResponseEntity<Void> updateFieldsEndereco(@PathVariable long id, Map<String, Object> fields){
		enderecoService.updateByFields(id, fields);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping(path = "/filiacoes")
	public ResponseEntity<List<Filiacao>> listFiliacao(){
		return ResponseEntity.ok(filiacaoService.listAll());
	}
	
	@GetMapping(path = "/filiacao/{id}")
    public ResponseEntity<Filiacao> findFiliacaoById(@PathVariable long id){
        return ResponseEntity.ok(filiacaoService.findFiliacao(id));
    }
	
	@DeleteMapping(path = "/deleteFiliacao/{id}")
	public ResponseEntity<Void> deleteFiliacao(@PathVariable long id){
		filiacaoService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping(path = "/postFiliacao")
	public ResponseEntity<Filiacao> saveFiliacao(@RequestBody FiliacaoPostRequestBody filiacaoPostRequestBody){
		return new ResponseEntity<>(filiacaoService.save(filiacaoPostRequestBody), HttpStatus.CREATED);
	}
	
	@PutMapping(path = "/putFiliacao")
	public ResponseEntity<Void> replaceFiliacao(@RequestBody FiliacaoPutRequestBody filiacaoPutRequestBody){
		filiacaoService.replace(filiacaoPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PatchMapping("/patchFiliacao/{id}")
	public ResponseEntity<Void> updateFieldsFiliacao(@PathVariable long id, Map<String, Object> fields){
		filiacaoService.updateByFields(id, fields);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping(path = "/naturalidades")
	public ResponseEntity<List<Naturalidade>> listNaturalidade(){
		return ResponseEntity.ok(naturalidadeService.listAll());
	}
	
	@GetMapping(path = "/naturalidade/{id}")
    public ResponseEntity<Naturalidade> findNaturalidadeById(@PathVariable long id){
        return ResponseEntity.ok(naturalidadeService.findNaturalidade(id));
    }
	
	@DeleteMapping(path = "/deleteNaturalidade/{id}")
	public ResponseEntity<Void> deleteNaturalidade(@PathVariable long id){
		dependentesService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping(path = "/postNaturalidade")
	public ResponseEntity<Naturalidade> saveNaturalidade(@RequestBody NaturalidadePostRequestBody naturalidadePostRequestBody){
		return new ResponseEntity<>(naturalidadeService.save(naturalidadePostRequestBody), HttpStatus.CREATED);
	}
	
	@PutMapping(path = "/putNaturalidade")
	public ResponseEntity<Void> replaceNaturalidade(@RequestBody NaturalidadePutRequestBody naturalidadePutRequestBody){
		naturalidadeService.replace(naturalidadePutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PatchMapping("/patchNaturalidade/{id}")
	public ResponseEntity<Void> updateFieldsNaturalidade(@PathVariable long id, Map<String, Object> fields){
		naturalidadeService.updateByFields(id, fields);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping(path = "/fotosAssociados")
	public ResponseEntity<List<FotoAssociado>> listFotoAssociado(){
		return ResponseEntity.ok(fotoAssociadoService.listAll());
	}
	
	@GetMapping(path = "/fotoAssociado/{id}")
    public ResponseEntity<FotoAssociado> findFotoAssociadoById(@PathVariable long id){
        return ResponseEntity.ok(fotoAssociadoService.findFotoAssociado(id));
    }
	
	@DeleteMapping(path = "/deleteFotoAssociado/{id}")
	public ResponseEntity<Void> deleteFotoAssociado(@PathVariable long id){
		fotoAssociadoService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping(path = "/postFotoAssociado")
	public ResponseEntity<FotoAssociado> saveFotoAssociado(@RequestBody FotoAssociadoPostRequestBody fotoAssociadoPostRequestBody){
		return new ResponseEntity<>(fotoAssociadoService.save(fotoAssociadoPostRequestBody), HttpStatus.CREATED);
	}
	
	@PutMapping(path = "/putFotoAssociado")
	public ResponseEntity<Void> replaceFotoAssociado(@RequestBody FotoAssociadoPutRequestBody fotoAssociadoPutRequestBody){
		fotoAssociadoService.replace(fotoAssociadoPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PatchMapping("/patchFotoAssociado/{id}")
	public ResponseEntity<Void> updateFieldsFotoAssociado(@PathVariable long id, Map<String, Object> fields){
		fotoAssociadoService.updateByFields(id, fields);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
