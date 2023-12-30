package com.ifba.educampo.repository;

import com.ifba.educampo.entity.associate.WorkRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRecordRepository extends JpaRepository<WorkRecord, Long> {
}
