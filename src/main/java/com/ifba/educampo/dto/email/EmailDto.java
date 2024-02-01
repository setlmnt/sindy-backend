package com.ifba.educampo.dto.email;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;
import java.util.Map;

/**
 * EmailDto
 */
public record EmailDto(
        @NotBlank
        String subject,
        @NotNull
        String message,
        @NotNull
        String[] recipients,
        @NotNull
        List<String> templatesName
) {
}
