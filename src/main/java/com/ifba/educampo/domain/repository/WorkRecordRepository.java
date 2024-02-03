package com.ifba.educampo.domain.repository;

import com.ifba.educampo.domain.entity.associate.WorkRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRecordRepository extends JpaRepository<WorkRecord, Long> {
}
