package com.ifba.educampo.repository.associate;

import com.ifba.educampo.model.entity.associate.WorkRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRecordRepository extends JpaRepository<WorkRecord, Long> { // Interface de repositório para a carteira de trabalho

}
