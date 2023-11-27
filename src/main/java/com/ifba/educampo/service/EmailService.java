package com.ifba.educampo.service;

import com.ifba.educampo.annotation.Log;
import com.ifba.educampo.dto.email.EmailDto;
import com.ifba.educampo.dto.email.EmailResponseDto;
import com.ifba.educampo.mapper.EmailMapper;
import com.ifba.educampo.model.entity.Email;
import com.ifba.educampo.model.enums.StatusEmail;
import com.ifba.educampo.repository.email.EmailRepository;
import com.ifba.educampo.repository.email.EmailRepositoryCustom;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
@Log
public class EmailService {
    private final EmailRepository emailRepository;
    private final EmailRepositoryCustom emailRepositoryCustom;
    private final EmailMapper emailMapper;
    private final JavaMailSender emailSender;

    public Page<EmailResponseDto> findAll(
            String owner,
            String emailTo,
            String emailFrom,
            StatusEmail status,
            Pageable pageable
    ) {
        return emailRepositoryCustom
                .findAllWithFilterAndDeletedFalse(owner, emailTo, emailFrom, status, pageable)
                .map(emailMapper::toResponseDto);
    }

    public EmailResponseDto send(EmailDto emailDto) {
        log.info("Sending email to {}", emailDto.emailTo());

        Email email = emailMapper.dtoToEntity(emailDto);

        try {
            SimpleMailMessage message = getEmailMessage(email);
            emailSender.send(message);

            email.setStatus(StatusEmail.SENT);
        } catch (Exception e) {
            email.setStatus(StatusEmail.ERROR);
        }

        return emailMapper.toResponseDto(emailRepository.save(email));
    }

    private SimpleMailMessage getEmailMessage(Email email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(email.getEmailFrom());
        message.setTo(email.getEmailTo());
        message.setSubject(email.getSubject());
        message.setText(email.getText());
        return message;
    }
}
