package com.ifba.educampo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.ifba.educampo.domain.Associado;
import com.ifba.educampo.exception.BadRequestException;
import com.ifba.educampo.repository.AssociadoRepository;
import com.ifba.educampo.requests.AssociadoPostRequestBody;
import com.ifba.educampo.requests.AssociadoPutRequestBody;

import java.lang.reflect.Field;
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
	
	public Page<Associado> findAssociadoByName(String nome, Pageable pageable) {
		return associadoRepository.findByNome(nome, pageable)
				.orElseThrow(()-> new BadRequestException("Associado Not Found"));
	}
	
	public Page<Associado> findAssociadoByCpf(String cpf, Pageable pageable) {
		return associadoRepository.findByCpf(cpf, pageable)
				.orElseThrow(()-> new BadRequestException("Associado Not Found"));
	}
	
	public Page<Associado> findAssociadoByCarteiraSindical(Long carteira, Pageable pageable) {
		return associadoRepository.findByCarteiraSindical(carteira, pageable)
				.orElseThrow(()-> new BadRequestException("Associado Not Found"));
	}
	
	public Page<Associado> listAll(Pageable pageable) {
        return associadoRepository.findAll(pageable);
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
										.endereco(enderecoService
												.replace(associadoPutRequestBody.getEndereco(), 
														savedAssociado.getEndereco().getId()))
										.dependentes(dependentesService
												.replace(associadoPutRequestBody.getDependentes(), 
												savedAssociado.getDependentes().getId()))
										.filiacao(filiacaoService
												.replace(associadoPutRequestBody.getFiliacao(),
												savedAssociado.getFiliacao().getId()))
										.naturalidade(naturalidadeService
												.replace(associadoPutRequestBody.getNaturalidade(),
												savedAssociado.getNaturalidade().getId()))
										.fotoAssociado(fotoAssociadoService
												.replace(associadoPutRequestBody.getFotoAssociado(),
												savedAssociado.getFotoAssociado().getId()))
										.carteiraProfissional(carteiraService
												.replace(associadoPutRequestBody.getCarteiraProfissional(),
												savedAssociado.getCarteiraProfissional().getId()))
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
