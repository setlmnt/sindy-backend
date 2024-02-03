package br.com.sindy.domain.repository;

import br.com.sindy.domain.entity.associate.WorkRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRecordRepository extends JpaRepository<WorkRecord, Long> {
}
