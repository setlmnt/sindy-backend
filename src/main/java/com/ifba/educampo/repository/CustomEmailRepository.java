package com.ifba.educampo.repository;

import com.ifba.educampo.dto.email.EmailResponseDto;
import com.ifba.educampo.enums.EmailStatusEnum;
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
