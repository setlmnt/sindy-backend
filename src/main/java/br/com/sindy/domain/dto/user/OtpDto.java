package br.com.sindy.domain.dto.user;

public record OtpDto(
        UserResponseDto user,
        String code
) {
}
