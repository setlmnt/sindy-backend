package br.com.sindy.domain.dto.email;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

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
