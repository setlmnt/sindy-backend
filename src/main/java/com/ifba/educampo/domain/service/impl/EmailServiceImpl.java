package com.ifba.educampo.domain.service.impl;

import com.ifba.educampo.core.annotation.Log;
import com.ifba.educampo.domain.dto.email.EmailDto;
import com.ifba.educampo.domain.dto.email.EmailResponseDto;
import com.ifba.educampo.domain.entity.Template;
import com.ifba.educampo.domain.entity.email.CommunicationHistory;
import com.ifba.educampo.domain.entity.email.Recipient;
import com.ifba.educampo.domain.enums.EmailStatusEnum;
import com.ifba.educampo.domain.repository.CommunicationHistoryRepository;
import com.ifba.educampo.domain.repository.CommunicationRecipientRepository;
import com.ifba.educampo.domain.repository.CustomEmailRepository;
import com.ifba.educampo.domain.repository.EmailTemplateRepository;
import com.ifba.educampo.domain.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
@Log
public class EmailServiceImpl implements EmailService {
    private final CommunicationHistoryRepository communicationHistoryRepository;
    private final CommunicationRecipientRepository communicationRecipientRepository;
    private final EmailTemplateRepository emailTemplateRepository;
    private final CustomEmailRepository customEmailRepository;
    private final JavaMailSender emailSender;
    @Value("${email.from}")
    private String emailFrom;
    @Value("${email.name}")
    private String emailName;

    public Page<EmailResponseDto> findAll(
            String sender,
            String recipient,
            EmailStatusEnum status,
            Pageable pageable
    ) {
        return customEmailRepository.findAllWithFilter(sender, recipient, status, pageable);
    }

    @Async
    @Transactional
    @Override
    public void send(EmailDto emailDto) {
        log.info("Sending email");

        CommunicationHistory communicationHistory = communicationHistoryRepository.save(getCommunicationHistory(emailDto));
        communicationRecipientRepository.saveAll(getCommunicationRecipients(emailDto, communicationHistory));

        try {
            MimeMessage message = getMessage(emailDto);
            emailSender.send(message);
            communicationHistory.setStatus(EmailStatusEnum.SENT);
            log.info("Email successfully");
        } catch (Exception e) {
            communicationHistory.setStatus(EmailStatusEnum.ERROR);
            log.error("Email error", e);
        }
    }

    @Override
    public String formatEmail(String name, String email) {
        return String.format("%s <%s>", name, email);
    }

    private MimeMessage getMessage(EmailDto emailDto) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(emailDto.recipients());
        helper.setSubject(emailDto.subject());
        helper.setText(emailDto.message(), true);
        helper.setFrom(formatEmail(emailName, emailFrom));
        return message;
    }

    private List<Recipient> getCommunicationRecipients(EmailDto emailDto, CommunicationHistory communicationHistory) {
        List<Recipient> recipients = new ArrayList<>();

        for (String recipient : emailDto.recipients()) {
            String[] recipientNameAndEmail = recipient.split(" ");
            String name = recipientNameAndEmail[0];
            String email = recipientNameAndEmail[1].replace("<", "").replace(">", "");

            Recipient communicationRecipient = new Recipient();
            communicationRecipient.setName(name);
            communicationRecipient.setEmail(email);
            communicationRecipient.setCommunicationHistory(communicationHistory);
            recipients.add(communicationRecipient);
        }

        return recipients;
    }

    private CommunicationHistory getCommunicationHistory(EmailDto emailDto) {
        CommunicationHistory communicationHistory = new CommunicationHistory();
        communicationHistory.setTemplates(getEmailTemplates(emailDto.templatesName()));
        communicationHistory.setSender(emailFrom);
        communicationHistory.setSubject(emailDto.subject());
        communicationHistory.setMessage(emailDto.message());
        return communicationHistory;
    }

    private List<Template> getEmailTemplates(List<String> names) {
        return emailTemplateRepository.findByNames(names);
    }
}
