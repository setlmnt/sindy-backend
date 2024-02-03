package com.ifba.educampo.domain.dto.associate.workRecord;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WorkRecordPostDto(
        @NotNull(message = "Number is required")
        Long number, // Número

        @NotNull(message = "Series is required")
        @NotBlank(message = "Series is required")
        String series // Série
) {
}
