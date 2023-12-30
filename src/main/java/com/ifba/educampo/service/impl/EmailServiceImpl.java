package com.ifba.educampo.service.impl;

import com.ifba.educampo.annotation.Log;
import com.ifba.educampo.dto.email.EmailDto;
import com.ifba.educampo.dto.email.EmailResponseDto;
import com.ifba.educampo.entity.Email;
import com.ifba.educampo.enums.StatusEmailEnum;
import com.ifba.educampo.mapper.EmailMapper;
import com.ifba.educampo.repository.EmailRepository;
import com.ifba.educampo.service.EmailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
@Log
public class EmailServiceImpl implements EmailService {
    private final EmailRepository emailRepository;
    private final EmailMapper emailMapper;
    private final JavaMailSender emailSender;

    public Page<EmailResponseDto> findAll(
            String owner,
            String emailTo,
            String emailFrom,
            StatusEmailEnum status,
            Pageable pageable
    ) {
        return emailRepository
                .findAllWithFilterAndDeletedFalse(owner, emailTo, emailFrom, status, pageable)
                .map(emailMapper::toResponseDto);
    }

    public Email save(EmailDto emailDto) {
        log.info("Saving email {}", emailDto);

        Email email = emailMapper.dtoToEntity(emailDto);
        return emailRepository.save(email);
    }

    public void send(EmailDto emailDto) {
        log.info("Sending email to {}", emailDto.emailTo());

        Email email = save(emailDto);

        try {
            SimpleMailMessage message = getEmailMessage(email);
            emailSender.send(message);

            email.setStatus(StatusEmailEnum.SENT);
            log.info("Email successfully sent to {}", emailDto.emailTo());
        } catch (Exception e) {
            email.setStatus(StatusEmailEnum.ERROR);
            log.error("Error sending email to {}", emailDto.emailTo());
        }
    }

    @Async
    public void sendAsync(EmailDto emailDto) {
        send(emailDto);
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
