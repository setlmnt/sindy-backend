package br.com.sindy.domain.repository;

import br.com.sindy.domain.entity.email.CommunicationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunicationHistoryRepository extends JpaRepository<CommunicationHistory, String>, CustomCommunicationHistoryRepository {
}
