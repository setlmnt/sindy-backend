package com.ifba.educampo.service;

import com.ifba.educampo.exception.NotFoundException;
import com.ifba.educampo.mapper.GenericMapper;
import com.ifba.educampo.model.dto.WorkRecordDto;
import com.ifba.educampo.model.entity.WorkRecord;
import com.ifba.educampo.repository.WorkRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkRecordService { // Carteira de trabalho
    private final GenericMapper<WorkRecordDto, WorkRecord> modelMapper;
    private final WorkRecordRepository workRecordRepository;

    public WorkRecord findWorkRecord(Long id) {
        return workRecordRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Work Record Not Found"));
    }

    public List<WorkRecord> listAll() {
        return workRecordRepository.findAll();
    }

    public void delete(long id) {
        workRecordRepository.delete(findWorkRecord(id));
    }

    @Transactional
    public WorkRecord save(WorkRecordDto workRecordDto) {
        return workRecordRepository.save(modelMapper.mapDtoToModel(workRecordDto, WorkRecord.class));
    }

    public WorkRecord replace(WorkRecordDto workRecordDto, Long carteiraId) {
        WorkRecord savedCarteira = findWorkRecord(carteiraId);
        workRecordDto.setId(savedCarteira.getId());

        return workRecordRepository.save(modelMapper.mapDtoToModel(workRecordDto, WorkRecord.class));
    }
}
