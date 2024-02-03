package com.ifba.educampo.domain.service.impl;

import com.ifba.educampo.core.annotation.Log;
import com.ifba.educampo.domain.dto.email.EmailDto;
import com.ifba.educampo.domain.dto.user.OtpDto;
import com.ifba.educampo.domain.dto.user.UserResponseDto;
import com.ifba.educampo.domain.entity.user.Otp;
import com.ifba.educampo.domain.enums.ErrorsEnum;
import com.ifba.educampo.domain.exception.ApiException;
import com.ifba.educampo.domain.mapper.OtpMapper;
import com.ifba.educampo.domain.repository.OtpRepository;
import com.ifba.educampo.domain.service.EmailService;
import com.ifba.educampo.domain.service.OtpService;
import com.ifba.educampo.domain.service.TemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
@Log
public class OtpServiceImpl implements OtpService {
    public static final String OTP_MESSAGE_TEMPLATE_ID = "change.password.otp.message";
    public static final String OTP_SUBJECT_TEMPLATE_ID = "change.password.otp.subject";

    private final OtpRepository otpRepository;
    private final OtpMapper otpMapper;
    private final EmailService emailService;
    private final TemplateService templateService;

    public String create(UserResponseDto userResponseDto) {
        log.info("Creating OTP for user {}", userResponseDto);

        otpRepository.deleteAllByUsername(userResponseDto.username());

        String otpCode = generateOtpCode();
        OtpDto otpDto = new OtpDto(userResponseDto, otpCode);

        otpRepository.save(otpMapper.dtoToEntity(otpDto));
        return otpCode;
    }

    private String generateOtpCode() {
        return new DecimalFormat("000000").format(Math.random() * 999999);
    }

    public void verifyOtp(String username, String code) {
        log.info("Verifying OTP {} for user {}", code, username);

        Otp otp = otpRepository.findByCodeAndUsername(code, username);

        if (otp == null) {
            throw new ApiException(ErrorsEnum.INVALID_OTP);
        }

        if (otp.isExpired()) {
            throw new ApiException(ErrorsEnum.OTP_EXPIRED);
        }

        otp.delete();
    }

    @Async
    public void sendOtpToEmail(UserResponseDto userResponseDto, String otp) {
        log.info("Sending OTP to {}", userResponseDto.email());

        String subject = getOtpMessageSubject(userResponseDto);
        String message = getOtpMessage(userResponseDto, otp);
        String[] recipients = getOtpRecipients(userResponseDto);
        List<String> templateIds = getOtpTemplateIds();

        emailService.send(
                new EmailDto(
                        subject,
                        message,
                        recipients,
                        templateIds
                )
        );
    }

    private List<String> getOtpTemplateIds() {
        return List.of(
                OTP_MESSAGE_TEMPLATE_ID,
                OTP_SUBJECT_TEMPLATE_ID
        );
    }

    private String getOtpMessage(UserResponseDto userResponseDto, String otp) {
        return templateService.getProcessedTemplate(
                OTP_MESSAGE_TEMPLATE_ID,
                Map.of(
                        "name", userResponseDto.username(),
                        "otp", otp
                )
        );
    }

    private String getOtpMessageSubject(UserResponseDto userResponseDto) {
        return templateService.getProcessedTemplate(
                OTP_SUBJECT_TEMPLATE_ID,
                Map.of(
                        "name", userResponseDto.username()
                )
        );
    }

    private String[] getOtpRecipients(UserResponseDto userResponseDto) {
        return new String[]{
                emailService.formatEmail(userResponseDto.username(), userResponseDto.email())
        };
    }
}
