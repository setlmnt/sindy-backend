package com.ifba.educampo.domain.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UserUpdatePasswordDto(
        @NotBlank
        String password
) {
}
