package com.ifba.educampo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifba.educampo.domain.WorkRecord;

public interface WorkRecordRepository extends JpaRepository<WorkRecord, Long>{ // Interface de repositório para a carteira de trabalho

}
