package com.ifba.educampo.domain.service;

import com.ifba.educampo.domain.dto.user.UserResponseDto;

public interface OtpService {
    String create(UserResponseDto userResponseDto);

    void verifyOtp(String username, String code);

    void sendOtpToEmail(UserResponseDto userResponseDto, String otp);
}
