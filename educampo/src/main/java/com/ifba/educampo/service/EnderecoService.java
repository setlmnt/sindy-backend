package com.ifba.educampo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifba.educampo.domain.Endereco;
import com.ifba.educampo.exception.BadRequestException;
import com.ifba.educampo.repository.EnderecoRepository;
import com.ifba.educampo.requests.EnderecoPostRequestBody;
import com.ifba.educampo.requests.EnderecoPutRequestBody;

import java.util.List;
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
						.cpf(enderecoPostRequestBody.getCpf())
						.build()
				);
	}
	
	public void replace(EnderecoPutRequestBody enderecoPutRequestBody) {
		Endereco savedEndereco = findEndereco(enderecoPutRequestBody.getId());
		Endereco endereco = Endereco.builder()
										.id(savedEndereco.getId())
										.rua(enderecoPutRequestBody.getRua())
										.numero(enderecoPutRequestBody.getNumero())
										.complemento(enderecoPutRequestBody.getComplemento())
										.bairro(enderecoPutRequestBody.getBairro())
										.cpf(enderecoPutRequestBody.getCpf())
										.build();
		
		enderecoRepository.save(endereco);
	}
	
}
