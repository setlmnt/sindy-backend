package com.ifba.educampo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifba.educampo.domain.Dependentes;
import com.ifba.educampo.exception.BadRequestException;
import com.ifba.educampo.repository.DependentesRepository;
import com.ifba.educampo.requests.DependentesPostRequestBody;
import com.ifba.educampo.requests.DependentesPutRequestBody;

import java.util.List;
import lombok.RequiredArgsConstructor;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class DependentesService {
	@Autowired
	private DependentesRepository dependentesRepository;
	
	public Dependentes findDependente(Long id) {
		return dependentesRepository.findById(id)
				.orElseThrow(()-> new BadRequestException("Dependente Not Found"));
	}
	
	public List<Dependentes> listAll() {
        return dependentesRepository.findAll();
    }
	
	public void delete(long id) {
		dependentesRepository.delete(findDependente(id));
	}
	
	@Transactional
	public Dependentes save(DependentesPostRequestBody dependentesPostRequestBody) {
		return dependentesRepository.save(Dependentes.builder()
						.filhosMenores(dependentesPostRequestBody.getFilhosMenores())
						.filhosHomens(dependentesPostRequestBody.getFilhosHomens())
						.filhosMulheres(dependentesPostRequestBody.getFilhosMulheres())
						.outrosDependentes(dependentesPostRequestBody.getOutrosDependentes())
						.build()
				);
	}
	
	public void replace(DependentesPutRequestBody dependentesPutRequestBody) {
		Dependentes savedDependentes = findDependente(dependentesPutRequestBody.getId());
		Dependentes dependentes = Dependentes.builder()
										.id(savedDependentes.getId())
										.filhosMenores(dependentesPutRequestBody.getFilhosMenores())
										.filhosHomens(dependentesPutRequestBody.getFilhosHomens())
										.filhosMulheres(dependentesPutRequestBody.getFilhosMulheres())
										.outrosDependentes(dependentesPutRequestBody.getOutrosDependentes())
										.build();
		
		dependentesRepository.save(dependentes);
	}
	
}
