package com.ifba.educampo.dto.localOffice;

import jakarta.validation.constraints.Size;

public record LocalOfficePutDto(
        @Size(min = 3, message = "Local Office Name must be at least 3 characters long")
        String name // Nome
) {
}
