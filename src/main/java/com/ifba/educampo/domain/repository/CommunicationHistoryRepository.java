package com.ifba.educampo.domain.repository;

import com.ifba.educampo.domain.entity.email.CommunicationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunicationHistoryRepository extends JpaRepository<CommunicationHistory, String> {
}
