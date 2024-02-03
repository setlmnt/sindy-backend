package com.ifba.educampo.domain.dto.user;

public record UserResponseDto(
        Long id,
        String username,
        String email
) {
}
