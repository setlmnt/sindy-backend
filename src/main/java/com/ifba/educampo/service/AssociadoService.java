package com.ifba.educampo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.ifba.educampo.domain.Associado;
import com.ifba.educampo.exception.BadRequestException;
import com.ifba.educampo.repository.AssociadoRepository;
import com.ifba.educampo.requests.AssociadoPostRequestBody;
import com.ifba.educampo.requests.AssociadoPutRequestBody;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AssociadoService {
	@Autowired
	private AssociadoRepository associadoRepository;
	@Autowired
	private EnderecoService enderecoService;
	@Autowired
	private CarteiraProfissionalService carteiraService;
	@Autowired
	private DependentesService dependentesService;
	@Autowired
	private FiliacaoService filiacaoService;
	@Autowired
	private FotoAssociadoService fotoAssociadoService;
	@Autowired
	private NaturalidadeService naturalidadeService;
	
	public Associado findAssociado(Long id) {
		return associadoRepository.findById(id)
				.orElseThrow(()-> new BadRequestException("Associado Not Found"));
	}
	
	public Associado findAssociadoByName(String nome) {
		return associadoRepository.findByNome(nome)
				.orElseThrow(()-> new BadRequestException("Associado Not Found"));
	}
	
	public Associado findAssociadoByCpf(String cpf) {
		return associadoRepository.findByCpf(cpf)
				.orElseThrow(()-> new BadRequestException("Associado Not Found"));
	}
	
	public Associado findAssociadoByCarteiraSindical(Long carteira) {
		return associadoRepository.findByCarteiraSindical(carteira)
				.orElseThrow(()-> new BadRequestException("Associado Not Found"));
	}
	
	public List<Associado> listAll() {
        return associadoRepository.findAll();
    }
	
	public void delete(long id) {
		associadoRepository.delete(findAssociado(id));
	}
	
	@Transactional
	public Associado save(AssociadoPostRequestBody associadoPostRequestBody) {
		return associadoRepository.save(Associado.builder()
						.nome(associadoPostRequestBody.getNome())
						.profissao(associadoPostRequestBody.getProfissao())		
						.nacionalidade(associadoPostRequestBody.getNacionalidade())	
						.dataNascimento(associadoPostRequestBody.getDataNascimento())	
						.dataAssociacao(associadoPostRequestBody.getDataAssociacao())	
						.localTrabalho(associadoPostRequestBody.getLocalTrabalho())	
						.carteiraSindical(associadoPostRequestBody.getCarteiraSindical())
						.cpf(associadoPostRequestBody.getCpf())	
						.rg(associadoPostRequestBody.getRg())
						.sabeLer(associadoPostRequestBody.isSabeLer())	
						.eleitor(associadoPostRequestBody.isEleitor())
						.estadoCivil(associadoPostRequestBody.getEstadoCivil())
						.endereco(enderecoService.save(associadoPostRequestBody.getEndereco()))
						.carteiraProfissional(carteiraService.save(associadoPostRequestBody.getCarteiraProfissional()))
						.dependentes(dependentesService.save(associadoPostRequestBody.getDependentes()))
						.filiacao(filiacaoService.save(associadoPostRequestBody.getFiliacao()))
						.fotoAssociado(fotoAssociadoService.save(associadoPostRequestBody.getFotoAssociado()))
						.naturalidade(naturalidadeService.save(associadoPostRequestBody.getNaturalidade()))
						.celular(associadoPostRequestBody.getCelular())					
						.build()
				);
	}
	
	public void replace(AssociadoPutRequestBody associadoPutRequestBody) {
		Associado savedAssociado = findAssociado(associadoPutRequestBody.getId());
		Associado associado = Associado.builder()
										.id(savedAssociado.getId())
										.nome(associadoPutRequestBody.getNome())
										.estadoCivil(associadoPutRequestBody.getEstadoCivil())
										.profissao(associadoPutRequestBody.getProfissao())
										.nacionalidade(associadoPutRequestBody.getNacionalidade())
										.dataNascimento(associadoPutRequestBody.getDataNascimento())
										.dataAssociacao(associadoPutRequestBody.getDataAssociacao())
										.localTrabalho(associadoPutRequestBody.getLocalTrabalho())
										.carteiraSindical(associadoPutRequestBody.getCarteiraSindical())
										.cpf(associadoPutRequestBody.getCpf())
										.rg(associadoPutRequestBody.getRg())
										.sabeLer(associadoPutRequestBody.isSabeLer())
										.eleitor(associadoPutRequestBody.isEleitor())
										.endereco(enderecoService.replace(associadoPutRequestBody.getEndereco()))
										.dependentes(dependentesService.replace(associadoPutRequestBody.getDependentes()))
										.filiacao(filiacaoService.replace(associadoPutRequestBody.getFiliacao()))
										.naturalidade(naturalidadeService.replace(associadoPutRequestBody.getNaturalidade()))
										.fotoAssociado(fotoAssociadoService.replace(associadoPutRequestBody.getFotoAssociado()))
										.carteiraProfissional(carteiraService.replace(associadoPutRequestBody.getCarteiraProfissional()))
										.celular(associadoPutRequestBody.getCelular())
										.build();
		
		associadoRepository.save(associado);
		
	}

	public void updateByFields(long id, Map<String, Object> fields) {
		Associado savedAssociado = findAssociado(id);
		
		fields.forEach((key,value)->{
			Field field = ReflectionUtils.findField(Associado.class, key);
			field.setAccessible(true);
			ReflectionUtils.setField(field, savedAssociado, value);
		});
		associadoRepository.save(savedAssociado);
	}
	
}
