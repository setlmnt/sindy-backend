package com.ifba.educampo.service.associate;

import com.ifba.educampo.dto.associate.workRecord.WorkRecordPostDto;
import com.ifba.educampo.dto.associate.workRecord.WorkRecordPutDto;
import com.ifba.educampo.mapper.associate.WorkRecordMapper;
import com.ifba.educampo.model.entity.associate.WorkRecord;
import com.ifba.educampo.repository.associate.WorkRecordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class WorkRecordService { // Carteira de trabalho
    private final WorkRecordMapper workRecordMapper;
    private final WorkRecordRepository workRecordRepository;

    public Page<WorkRecord> listAll(Pageable pageable) {
        log.info("Listing all work records.");
        return workRecordRepository.findAll(pageable);
    }

    public WorkRecord save(WorkRecordPostDto dto) {
        log.info("Saving work record.");
        return workRecordRepository.save(workRecordMapper.postDtoToEntity(dto));
    }

    public WorkRecord update(Long id, WorkRecordPutDto dto) {
        log.info("Replacing work record with ID: {}", id);
        WorkRecord workRecord = workRecordRepository.getReferenceById(id);
        workRecord.update(workRecordMapper.putDtoToEntity(dto));

        return workRecord;
    }

    public void delete(Long id) {
        log.info("Deleting work record with ID: {}", id);
        WorkRecord workRecord = workRecordRepository.getReferenceById(id);
        workRecord.delete();
    }
}
