package com.ifba.educampo.dto.associate.affiliation;

import jakarta.validation.constraints.Size;

public record AffiliationPutDto(
        @Size(min = 3, message = "Father name must be at least 3 characters long")
        String fatherName, // Nome do Pai

        @Size(min = 3, message = "Mother name must be at least 3 characters long")
        String motherName // Nome da Mãe
) {
}
