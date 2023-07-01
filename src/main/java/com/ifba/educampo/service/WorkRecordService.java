package com.ifba.educampo.service;

import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.ifba.educampo.domain.WorkRecord;
import com.ifba.educampo.exception.BadRequestException;
import com.ifba.educampo.repository.WorkRecordRepository;
import com.ifba.educampo.requests.WorkRecordPutRequestBody;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class WorkRecordService { // Carteira de trabalho
	private final WorkRecordRepository workRecordRepository;
	
	public WorkRecord findWorkRecord(Long id) {
		return workRecordRepository.findById(id)
				.orElseThrow(()-> new BadRequestException("Work Record Not Found"));
	}
	
	public List<WorkRecord> listAll() {
        return workRecordRepository.findAll();
    }
	
	public void delete(long id) {
		workRecordRepository.delete(findWorkRecord(id));
	}
	
	@Transactional
	public WorkRecord save(WorkRecordPutRequestBody workRecordPostRequestBody) {
		return workRecordRepository.save(WorkRecord.builder()
						.number(workRecordPostRequestBody.getNumber())
						.series(workRecordPostRequestBody.getSeries())
						.build()
				);
	}
	
	public WorkRecord replace(WorkRecordPutRequestBody workRecordPutRequestBody, Long carteiraId) {
		WorkRecord savedCarteira = findWorkRecord(carteiraId);
		return workRecordRepository.save(WorkRecord.builder()
										.id(savedCarteira.getId())
										.number(workRecordPutRequestBody.getNumber())
										.series(workRecordPutRequestBody.getSeries())
										.build());
		
	}
	
	public void updateByFields(long id, Map<String, Object> fields) {
		WorkRecord savedWorkRecord = findWorkRecord(id);
		
		fields.forEach((key,value)->{
			Field field = ReflectionUtils.findField(WorkRecord.class, key);
			field.setAccessible(true);
			ReflectionUtils.setField(field, savedWorkRecord, value);
		});
		workRecordRepository.save(savedWorkRecord);
	}
	
}
