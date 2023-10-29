package com.ifba.educampo.service;

import com.ifba.educampo.dto.WorkRecordDto;
import com.ifba.educampo.exception.NotFoundException;
import com.ifba.educampo.mapper.GenericMapper;
import com.ifba.educampo.model.entity.WorkRecord;
import com.ifba.educampo.repository.WorkRecordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkRecordService { // Carteira de trabalho
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkRecordService.class);
    private final GenericMapper<WorkRecordDto, WorkRecord> modelMapper;
    private final WorkRecordRepository workRecordRepository;

    public WorkRecord findWorkRecord(Long id) {
        LOGGER.info("Finding work record with ID: {}", id);
        return workRecordRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Work record with ID {} not found.", id);
                    return new NotFoundException("Work Record Not Found");
                });
    }

    public List<WorkRecord> listAll() {
        try {
            LOGGER.info("Listing all work records.");
            return workRecordRepository.findAll();
        } catch (Exception e) {
            LOGGER.error("An error occurred while listing work records.", e);
            throw new NotFoundException("An error occurred while listing work records.");
        }
    }

    @Transactional
    public void delete(long id) {
        try {
            LOGGER.info("Deleting work record with ID: {}", id);
            workRecordRepository.deleteById(id);
        } catch (Exception e) {
            LOGGER.error("An error occurred while listing work records.", e);
            throw new NotFoundException("An error occurred while deleting work record.");
        }
    }

    @Transactional
    public WorkRecord save(WorkRecordDto workRecordDto) {
        try {
            LOGGER.info("Saving work record.");
            return workRecordRepository.save(modelMapper.mapDtoToModel(workRecordDto, WorkRecord.class));
        } catch (Exception e) {
            LOGGER.error("An error occurred while saving work record.", e);
            throw new NotFoundException("An error occurred while saving work record.");
        }
    }

    @Transactional
    public WorkRecord replace(WorkRecordDto workRecordDto, Long carteiraId) {
        try {
            LOGGER.info("Replacing work record with ID: {}", carteiraId);
            WorkRecord savedWorkRecord = findWorkRecord(carteiraId);
            workRecordDto.setId(savedWorkRecord.getId());

            return workRecordRepository.save(modelMapper.mapDtoToModel(workRecordDto, WorkRecord.class));
        } catch (Exception e) {
            LOGGER.error("An error occurred while replacing work record.", e);
            throw new NotFoundException("An error occurred while replacing work record.");
        }
    }
}
