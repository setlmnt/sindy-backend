package com.ifba.educampo.domain.service;

import com.ifba.educampo.domain.dto.associate.workRecord.WorkRecordPostDto;
import com.ifba.educampo.domain.dto.associate.workRecord.WorkRecordPutDto;
import com.ifba.educampo.domain.entity.associate.WorkRecord;

public interface WorkRecordService {
    WorkRecord save(WorkRecordPostDto dto);

    WorkRecord update(Long id, WorkRecordPutDto dto);

    void delete(Long id);
}
