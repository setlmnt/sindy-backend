package br.com.sindy.domain.service;

import br.com.sindy.domain.dto.associate.workRecord.WorkRecordPostDto;
import br.com.sindy.domain.dto.associate.workRecord.WorkRecordPutDto;
import br.com.sindy.domain.entity.associate.WorkRecord;

public interface WorkRecordService {
    WorkRecord save(WorkRecordPostDto dto);

    WorkRecord update(Long id, WorkRecordPutDto dto);

    void delete(Long id);
}
