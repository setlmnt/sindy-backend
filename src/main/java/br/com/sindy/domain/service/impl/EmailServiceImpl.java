package br.com.sindy.domain.service.impl;

import br.com.sindy.core.annotation.Log;
import br.com.sindy.domain.dto.email.EmailDto;
import br.com.sindy.domain.dto.email.EmailResponseDto;
import br.com.sindy.domain.entity.Template;
import br.com.sindy.domain.entity.email.CommunicationHistory;
import br.com.sindy.domain.entity.email.Recipient;
import br.com.sindy.domain.enums.EmailStatusEnum;
import br.com.sindy.domain.repository.CommunicationHistoryRepository;
import br.com.sindy.domain.repository.CommunicationRecipientRepository;
import br.com.sindy.domain.repository.CustomEmailRepository;
import br.com.sindy.domain.repository.EmailTemplateRepository;
import br.com.sindy.domain.service.EmailService;
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

    @Override
    public String[] unformatEmail(String email) {
        String[] nameAndEmail = email.split(" ");
        nameAndEmail[1] = nameAndEmail[1].replace("<", "").replace(">", "");
        return nameAndEmail;
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
            String[] recipientData = unformatEmail(recipient);
            String name = recipientData[0];
            String email = recipientData[1];

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
        communicationHistory.setSenderName(emailName);
        communicationHistory.setSenderEmail(emailFrom);
        communicationHistory.setSubject(emailDto.subject());
        communicationHistory.setMessage(emailDto.message());
        return communicationHistory;
    }

    private List<Template> getEmailTemplates(List<String> names) {
        return emailTemplateRepository.findByNames(names);
    }
}
