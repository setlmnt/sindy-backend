package com.ifba.educampo.service;

import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.ifba.educampo.domain.Dependents;
import com.ifba.educampo.exception.BadRequestException;
import com.ifba.educampo.repository.DependentsRepository;
import com.ifba.educampo.requests.DependentsPutRequestBody;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class DependentsService { // Dependentes
	private final DependentsRepository dependentsRepository;
	
	public Dependents findDependent(Long id) {
		return dependentsRepository.findById(id)
				.orElseThrow(()-> new BadRequestException("Dependent Not Found"));
	}
	
	public List<Dependents> listAll() {
        return dependentsRepository.findAll();
    }
	
	public void delete(long id) {
		dependentsRepository.delete(findDependent(id));
	}
	
	@Transactional
	public Dependents save(DependentsPutRequestBody dependentsPostRequestBody) {
		return dependentsRepository.save(Dependents.builder()
						.wifeName(dependentsPostRequestBody.getWifeName())
						.minorChildren(dependentsPostRequestBody.getMinorChildren())
						.maleChildren(dependentsPostRequestBody.getMaleChildren())
						.femaleChildren(dependentsPostRequestBody.getFemaleChildren())
						.otherDependents(dependentsPostRequestBody.getOtherDependents())
						.build()
				);
	}
	
	public Dependents replace(DependentsPutRequestBody dependentsPutRequestBody, Long dependentId) {
		Dependents savedDependents = findDependent(dependentId);
		return dependentsRepository.save(Dependents.builder()
										.id(savedDependents.getId())
										.wifeName(dependentsPutRequestBody.getWifeName())
										.minorChildren(dependentsPutRequestBody.getMinorChildren())
										.maleChildren(dependentsPutRequestBody.getMaleChildren())
										.femaleChildren(dependentsPutRequestBody.getFemaleChildren())
										.otherDependents(dependentsPutRequestBody.getOtherDependents())
										.build());
	}
	
	public void updateByFields(long id, Map<String, Object> fields) {
		Dependents savedDependents = findDependent(id);
		
		fields.forEach((key,value)->{
			Field field = ReflectionUtils.findField(Dependents.class, key);
			field.setAccessible(true);
			ReflectionUtils.setField(field, savedDependents, value);
		});
		dependentsRepository.save(savedDependents);
	}
	
}
