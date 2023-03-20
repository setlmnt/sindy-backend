package com.ifba.educampo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.ifba.educampo.domain.Dependentes;
import com.ifba.educampo.exception.BadRequestException;
import com.ifba.educampo.repository.DependentesRepository;
import com.ifba.educampo.requests.DependentesPostRequestBody;
import com.ifba.educampo.requests.DependentesPutRequestBody;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

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
						.nomeEsposa(dependentesPostRequestBody.getNomeEsposa())
						.filhosMenores(dependentesPostRequestBody.getFilhosMenores())
						.filhosHomens(dependentesPostRequestBody.getFilhosHomens())
						.filhosMulheres(dependentesPostRequestBody.getFilhosMulheres())
						.outrosDependentes(dependentesPostRequestBody.getOutrosDependentes())
						.build()
				);
	}
	
	public Dependentes replace(DependentesPutRequestBody dependentesPutRequestBody) {
		Dependentes savedDependentes = findDependente(dependentesPutRequestBody.getId());
		return dependentesRepository.save(Dependentes.builder()
										.id(savedDependentes.getId())
										.nomeEsposa(dependentesPutRequestBody.getNomeEsposa())
										.filhosMenores(dependentesPutRequestBody.getFilhosMenores())
										.filhosHomens(dependentesPutRequestBody.getFilhosHomens())
										.filhosMulheres(dependentesPutRequestBody.getFilhosMulheres())
										.outrosDependentes(dependentesPutRequestBody.getOutrosDependentes())
										.build());
	}
	
	public void updateByFields(long id, Map<String, Object> fields) {
		// TODO Auto-generated method stub
		Dependentes savedDependentes = findDependente(id);
		
		fields.forEach((key,value)->{
			Field field = ReflectionUtils.findField(Dependentes.class, key);
			field.setAccessible(true);
			ReflectionUtils.setField(field, savedDependentes, value);
		});
		dependentesRepository.save(savedDependentes);
	}
	
}
