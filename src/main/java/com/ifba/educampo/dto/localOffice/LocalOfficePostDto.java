package com.ifba.educampo.dto.localOffice;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LocalOfficePostDto(
        @NotNull(message = "Local Office Name is required")
        @NotBlank(message = "Local Office Name is required")
        @Size(min = 3, message = "Local Office Name must be at least 3 characters long")
        String name // Nome
) {
}
