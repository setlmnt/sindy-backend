package br.com.sindy.domain.dto.user;

public record UserResponseDto(
        Long id,
        String username,
        String email
) {
}
