package br.com.sindy.domain.service;

import br.com.sindy.domain.dto.email.EmailDto;
import br.com.sindy.domain.dto.email.EmailResponseDto;
import br.com.sindy.domain.enums.EmailStatusEnum;
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

    String[] unformatEmail(String email);
}
