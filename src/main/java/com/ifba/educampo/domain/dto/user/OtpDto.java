package com.ifba.educampo.domain.dto.user;

public record OtpDto(
        UserResponseDto user,
        String code
) {
}
