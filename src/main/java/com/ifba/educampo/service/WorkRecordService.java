package com.ifba.educampo.service;

import com.ifba.educampo.dto.associate.workRecord.WorkRecordPostDto;
import com.ifba.educampo.dto.associate.workRecord.WorkRecordPutDto;
import com.ifba.educampo.entity.associate.WorkRecord;

public interface WorkRecordService {
    WorkRecord save(WorkRecordPostDto dto);

    WorkRecord update(Long id, WorkRecordPutDto dto);

    void delete(Long id);
}
