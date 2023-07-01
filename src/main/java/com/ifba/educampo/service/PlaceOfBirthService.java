package com.ifba.educampo.service;

import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.ifba.educampo.domain.PlaceOfBirth;
import com.ifba.educampo.exception.BadRequestException;
import com.ifba.educampo.repository.PlaceOfBirthRepository;
import com.ifba.educampo.requests.PlaceOfBirthPutRequestBody;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PlaceOfBirthService { // Naturalidade
	private final PlaceOfBirthRepository placeOfBirthRepository;
	
	public PlaceOfBirth findPlaceOfBirth(Long id) {
		return placeOfBirthRepository.findById(id)
				.orElseThrow(()-> new BadRequestException("Place Of Birth Not Found"));
	}
	
	public List<PlaceOfBirth> listAll() {
        return placeOfBirthRepository.findAll();
    }
	
	public void delete(long id) {
		placeOfBirthRepository.delete(findPlaceOfBirth(id));
	}
	
	@Transactional
	public PlaceOfBirth save(PlaceOfBirthPutRequestBody placeOfBirthPostRequestBody) {
		return placeOfBirthRepository.save(PlaceOfBirth.builder()
						.city(placeOfBirthPostRequestBody.getCity())
						.state(placeOfBirthPostRequestBody.getState())
						.build()
				);
	}
	
	public PlaceOfBirth replace(PlaceOfBirthPutRequestBody placeOfBirthPutRequestBody, Long placeOfBirthId) {
		PlaceOfBirth savedPlaceOfBirth = findPlaceOfBirth(placeOfBirthId);
		return placeOfBirthRepository.save(PlaceOfBirth.builder()
										.id(savedPlaceOfBirth.getId())
										.city(placeOfBirthPutRequestBody.getCity())
										.state(placeOfBirthPutRequestBody.getState())
										.build());
	}
	
	public void updateByFields(long id, Map<String, Object> fields) {
		PlaceOfBirth savedPlaceOfBirth = findPlaceOfBirth(id);
		
		fields.forEach((key,value)->{
			Field field = ReflectionUtils.findField(PlaceOfBirth.class, key);
			field.setAccessible(true);
			ReflectionUtils.setField(field, savedPlaceOfBirth, value);
		});
		placeOfBirthRepository.save(savedPlaceOfBirth);
	}
	
}
