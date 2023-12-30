package com.ifba.educampo.dto.email;

import jakarta.validation.constraints.NotBlank;

/**
 * EmailDto
 */
public record EmailDto(
        @NotBlank
        String owner,
        @NotBlank
        @jakarta.validation.constraints.Email
        String emailFrom,
        @NotBlank
        @jakarta.validation.constraints.Email
        String emailTo,
        @NotBlank
        String subject,
        @NotBlank
        String text
) {
}
