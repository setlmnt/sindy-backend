package br.com.sindy.domain.service.impl;

import br.com.sindy.core.annotation.Log;
import br.com.sindy.domain.dto.associate.workRecord.WorkRecordPostDto;
import br.com.sindy.domain.dto.associate.workRecord.WorkRecordPutDto;
import br.com.sindy.domain.entity.associate.WorkRecord;
import br.com.sindy.domain.mapper.associate.WorkRecordMapper;
import br.com.sindy.domain.repository.WorkRecordRepository;
import br.com.sindy.domain.service.WorkRecordService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@Log
public class WorkRecordServiceImpl implements WorkRecordService {
    private final WorkRecordMapper workRecordMapper;
    private final WorkRecordRepository workRecordRepository;

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
