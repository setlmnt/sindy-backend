package com.ifba.educampo.service.user;

import com.ifba.educampo.annotation.Log;
import com.ifba.educampo.dto.email.EmailDto;
import com.ifba.educampo.dto.user.OtpDto;
import com.ifba.educampo.dto.user.UserResponseDto;
import com.ifba.educampo.entity.user.Otp;
import com.ifba.educampo.exception.BadRequestException;
import com.ifba.educampo.mapper.OtpMapper;
import com.ifba.educampo.repository.user.OtpRepository;
import com.ifba.educampo.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
@Log
public class OtpService {
    private final OtpRepository otpRepository;
    private final OtpMapper otpMapper;
    private final EmailService emailService;
    private final Environment environment;

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
            throw new BadRequestException("Invalid OTP");
        }

        if (otp.isExpired()) {
            throw new BadRequestException("OTP expired");
        }

        otp.delete();
    }

    public void sendOtpToEmail(UserResponseDto userResponseDto, String otp) {
        log.info("Sending OTP: {} to {}", otp, userResponseDto.email());

        String subject = getSubject();
        String message = getMessage(otp);

        emailService.send(
                new EmailDto(
                        userResponseDto.username(),
                        environment.getProperty("spring.mail.username"),
                        userResponseDto.email(),
                        subject,
                        message
                )
        );
    }

    private String getSubject() {
        return "Mude sua senha!";
    }

    private String getMessage(String otp) {
        return "Mude sua senha. Seu código de verificação é: " + otp + ". Este código expirará em 30 minutos.";
    }
}
