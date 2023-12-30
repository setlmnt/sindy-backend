package com.ifba.educampo.dto.user;

public record UserResponseDto(
        Long id,
        String username,
        String email
) {
}
