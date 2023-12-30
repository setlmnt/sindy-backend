package com.ifba.educampo.service;

import com.ifba.educampo.dto.user.UserResponseDto;

public interface OtpService {
    String create(UserResponseDto userResponseDto);

    void verifyOtp(String username, String code);

    void sendOtpToEmailAsync(UserResponseDto userResponseDto, String otp);
}
