package com.ifba.educampo.domain.service;

import com.ifba.educampo.domain.dto.email.EmailDto;
import com.ifba.educampo.domain.dto.email.EmailResponseDto;
import com.ifba.educampo.domain.enums.EmailStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmailService {
    Page<EmailResponseDto> findAll(
            String sender,
            String recipient,
            EmailStatusEnum status,
            Pageable pageable
    );

    void send(EmailDto emailDto);

    String formatEmail(String name, String email);
}
