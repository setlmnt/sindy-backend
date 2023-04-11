package com.ifba.educampo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.ifba.educampo.domain.Endereco;
import com.ifba.educampo.exception.BadRequestException;
import com.ifba.educampo.repository.EnderecoRepository;
import com.ifba.educampo.requests.EnderecoPostRequestBody;
import com.ifba.educampo.requests.EnderecoPutRequestBody;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class EnderecoService {
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Endereco findEndereco(Long id) {
		return enderecoRepository.findById(id)
				.orElseThrow(()-> new BadRequestException("Endereco Not Found"));
	}
	
	public List<Endereco> listAll() {
        return enderecoRepository.findAll();
    }
	
	public void delete(long id) {
		enderecoRepository.delete(findEndereco(id));
	}
	
	@Transactional
	public Endereco save(EnderecoPostRequestBody enderecoPostRequestBody) {
		return enderecoRepository.save(Endereco.builder()
						.rua(enderecoPostRequestBody.getRua())
						.numero(enderecoPostRequestBody.getNumero())
						.complemento(enderecoPostRequestBody.getComplemento())
						.bairro(enderecoPostRequestBody.getBairro())
						.cep(enderecoPostRequestBody.getCep())
						.build()
				);
	}
	
	public Endereco replace(EnderecoPutRequestBody enderecoPutRequestBody, Long enderecoId) {
		Endereco savedEndereco = findEndereco(enderecoId);
		return enderecoRepository.save(Endereco.builder()
										.id(savedEndereco.getId())
										.rua(enderecoPutRequestBody.getRua())
										.numero(enderecoPutRequestBody.getNumero())
										.complemento(enderecoPutRequestBody.getComplemento())
										.bairro(enderecoPutRequestBody.getBairro())
										.cep(enderecoPutRequestBody.getCep())
										.build());
		
	}
	
	public void updateByFields(long id, Map<String, Object> fields) {
		// TODO Auto-generated method stub
		Endereco savedEndereco = findEndereco(id);
		
		fields.forEach((key,value)->{
			Field field = ReflectionUtils.findField(Endereco.class, key);
			field.setAccessible(true);
			ReflectionUtils.setField(field, savedEndereco, value);
		});
		enderecoRepository.save(savedEndereco);
	}
	
}
