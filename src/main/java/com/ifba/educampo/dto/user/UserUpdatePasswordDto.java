package com.ifba.educampo.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UserUpdatePasswordDto(
        @NotBlank
        String password
) {
}
