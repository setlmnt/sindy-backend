package br.com.sindy.domain.repository;

import br.com.sindy.domain.dto.email.EmailResponseDto;
import br.com.sindy.domain.enums.EmailStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomEmailRepository {
    Page<EmailResponseDto> findAllWithFilter(
            String sender,
            String recipient,
            EmailStatusEnum status,
            Pageable pageable
    );
}
