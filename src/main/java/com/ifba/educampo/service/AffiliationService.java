package com.ifba.educampo.service;

import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.ifba.educampo.domain.Affiliation;
import com.ifba.educampo.exception.BadRequestException;
import com.ifba.educampo.repository.AffiliationRepository;
import com.ifba.educampo.requests.AffiliationPutRequestBody;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AffiliationService { // Afiliacao
	private final AffiliationRepository affiliationRepository;
	
	public Affiliation findAffiliation(Long id) {
		return affiliationRepository.findById(id)
				.orElseThrow(()-> new BadRequestException("Affiliation Not Found"));
	}
	
	public List<Affiliation> listAll() {
        return affiliationRepository.findAll();
    }
	
	public void delete(long id) {
		affiliationRepository.delete(findAffiliation(id));
	}
	
	@Transactional
	public Affiliation save(AffiliationPutRequestBody affiliationPostRequestBody) {
		return affiliationRepository.save(Affiliation.builder()
						.fatherName(affiliationPostRequestBody.getFatherName())
						.motherName(affiliationPostRequestBody.getMotherName())
						.build()
				);
	}
	
	public Affiliation replace(AffiliationPutRequestBody affiliationPutRequestBody, Long affiliationId) {
		Affiliation savedAffiliation = findAffiliation(affiliationId);
		return affiliationRepository.save(Affiliation.builder()
										.id(savedAffiliation.getId())
										.fatherName(affiliationPutRequestBody.getFatherName())
										.motherName(affiliationPutRequestBody.getMotherName())
										.build());
	}
	
	public void updateByFields(long id, Map<String, Object> fields) {
		// TODO Auto-generated method stub
		Affiliation savedAffiliation = findAffiliation(id);
		
		fields.forEach((key,value)->{
			Field field = ReflectionUtils.findField(Affiliation.class, key);
			field.setAccessible(true);
			ReflectionUtils.setField(field, savedAffiliation, value);
		});
		affiliationRepository.save(savedAffiliation);
	}
	
}
