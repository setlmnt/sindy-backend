package br.com.sindy.domain.service.impl;

import br.com.sindy.core.annotation.Log;
import br.com.sindy.domain.dto.email.EmailDto;
import br.com.sindy.domain.dto.user.OtpDto;
import br.com.sindy.domain.dto.user.UserResponseDto;
import br.com.sindy.domain.entity.user.Otp;
import br.com.sindy.domain.enums.ErrorEnum;
import br.com.sindy.domain.exception.ApiException;
import br.com.sindy.domain.mapper.OtpMapper;
import br.com.sindy.domain.repository.OtpRepository;
import br.com.sindy.domain.service.EmailService;
import br.com.sindy.domain.service.OtpService;
import br.com.sindy.domain.service.TemplateService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

@Service
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

    @Transactional
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
            throw new ApiException(ErrorEnum.INVALID_OTP);
        }

        if (otp.isExpired()) {
            throw new ApiException(ErrorEnum.OTP_EXPIRED);
        }

        otp.delete();
    }

    @Async
    @Transactional
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
