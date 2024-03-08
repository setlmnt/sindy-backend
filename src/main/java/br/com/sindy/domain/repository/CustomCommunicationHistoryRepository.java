package br.com.sindy.domain.repository;

import br.com.sindy.domain.entity.email.CommunicationHistory;
import br.com.sindy.domain.enums.EmailStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomCommunicationHistoryRepository {
    Page<CommunicationHistory> findAllWithFilter(String sender, String recipient, EmailStatusEnum status, Pageable pageable);
}
