package br.com.sindy.domain.service;

import br.com.sindy.domain.dto.user.UserResponseDto;

public interface OtpService {
    String create(UserResponseDto userResponseDto);

    void verifyOtp(String username, String code);

    void sendOtpToEmail(UserResponseDto userResponseDto, String otp);
}
