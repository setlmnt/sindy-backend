package br.com.sindy.domain.repository;

import br.com.sindy.domain.entity.email.CommunicationHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunicationHistoryRepository extends JpaRepository<CommunicationHistory, String> {
    Page<CommunicationHistory> findAll(Specification<CommunicationHistory> spec, Pageable pageable);
}
