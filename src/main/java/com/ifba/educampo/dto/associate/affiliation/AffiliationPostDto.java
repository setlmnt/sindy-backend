package com.ifba.educampo.dto.associate.affiliation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AffiliationPostDto(
        @NotNull(message = "Father name is required")
        @NotBlank(message = "Father name is required")
        @Size(min = 3, message = "Father name must be at least 3 characters long")
        String fatherName, // Nome do Pai

        @NotNull(message = "Mother name is required")
        @NotBlank(message = "Mother name is required")
        @Size(min = 3, message = "Mother name must be at least 3 characters long")
        String motherName // Nome da Mãe
) {
}
