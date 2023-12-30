package com.ifba.educampo.service;

import com.ifba.educampo.dto.email.EmailDto;
import com.ifba.educampo.dto.email.EmailResponseDto;
import com.ifba.educampo.entity.Email;
import com.ifba.educampo.enums.StatusEmailEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmailService {
    Page<EmailResponseDto> findAll(
            String owner,
            String emailTo,
            String emailFrom,
            StatusEmailEnum status,
            Pageable pageable
    );

    Email save(EmailDto emailDto);

    void send(EmailDto emailDto);

    void sendAsync(EmailDto emailDto);
}
