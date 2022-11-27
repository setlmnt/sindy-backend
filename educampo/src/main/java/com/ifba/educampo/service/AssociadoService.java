package com.ifba.educampo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifba.educampo.domain.Associado;
import com.ifba.educampo.exception.BadRequestException;
import com.ifba.educampo.repository.AssociadoRepository;
import com.ifba.educampo.requests.AssociadoPostRequestBody;
import com.ifba.educampo.requests.AssociadoPutRequestBody;

import java.util.List;
import lombok.RequiredArgsConstructor;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AssociadoService {
	@Autowired
	private AssociadoRepository associadoRepository;
	
	public Associado findAssociado(Long id) {
		return associadoRepository.findById(id)
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
						.build()
				);
	}
	
	public void replace(AssociadoPutRequestBody associadoPutRequestBody) {
		Associado savedAssociado = findAssociado(associadoPutRequestBody.getId());
		Associado associado = Associado.builder()
										.id(savedAssociado.getId())
										.nome(associadoPutRequestBody.getNome())
										.build();
		
		associadoRepository.save(associado);
	}
	
}
