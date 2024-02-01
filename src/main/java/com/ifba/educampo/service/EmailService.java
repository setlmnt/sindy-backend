package com.ifba.educampo.service;

import com.ifba.educampo.dto.email.EmailDto;
import com.ifba.educampo.dto.email.EmailResponseDto;
import com.ifba.educampo.enums.EmailStatusEnum;
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
