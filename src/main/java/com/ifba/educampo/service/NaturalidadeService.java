package com.ifba.educampo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.ifba.educampo.domain.Naturalidade;
import com.ifba.educampo.exception.BadRequestException;
import com.ifba.educampo.repository.NaturalidadeRepository;
import com.ifba.educampo.requests.NaturalidadePostRequestBody;
import com.ifba.educampo.requests.NaturalidadePutRequestBody;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class NaturalidadeService {
	@Autowired
	private NaturalidadeRepository naturalidadeRepository;
	
	public Naturalidade findNaturalidade(Long id) {
		return naturalidadeRepository.findById(id)
				.orElseThrow(()-> new BadRequestException("Naturalidade Not Found"));
	}
	
	public List<Naturalidade> listAll() {
        return naturalidadeRepository.findAll();
    }
	
	public void delete(long id) {
		naturalidadeRepository.delete(findNaturalidade(id));
	}
	
	@Transactional
	public Naturalidade save(NaturalidadePostRequestBody naturalidadePostRequestBody) {
		return naturalidadeRepository.save(Naturalidade.builder()
						.municipio(naturalidadePostRequestBody.getMunicipio())
						.estado(naturalidadePostRequestBody.getEstado())
						.build()
				);
	}
	
	public Naturalidade replace(NaturalidadePutRequestBody naturalidadePutRequestBody) {
		Naturalidade savedNaturalidade = findNaturalidade(naturalidadePutRequestBody.getId());
		return naturalidadeRepository.save(Naturalidade.builder()
										.id(savedNaturalidade.getId())
										.municipio(naturalidadePutRequestBody.getMunicipio())
										.estado(naturalidadePutRequestBody.getEstado())
										.build());
	}
	
	public void updateByFields(long id, Map<String, Object> fields) {
		// TODO Auto-generated method stub
		Naturalidade savedNaturalidade = findNaturalidade(id);
		
		fields.forEach((key,value)->{
			Field field = ReflectionUtils.findField(Naturalidade.class, key);
			field.setAccessible(true);
			ReflectionUtils.setField(field, savedNaturalidade, value);
		});
		naturalidadeRepository.save(savedNaturalidade);
	}
	
}
