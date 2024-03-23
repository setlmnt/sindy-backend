package br.com.sindy.domain.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UserUpdatePasswordDto(
        @NotBlank
        String password
) {
}
