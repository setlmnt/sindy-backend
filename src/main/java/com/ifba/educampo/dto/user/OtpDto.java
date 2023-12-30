package com.ifba.educampo.dto.user;

public record OtpDto(
        UserResponseDto user,
        String code
) {
}
