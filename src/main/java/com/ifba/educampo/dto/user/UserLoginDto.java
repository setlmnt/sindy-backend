package com.ifba.educampo.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UserLoginDto(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}
