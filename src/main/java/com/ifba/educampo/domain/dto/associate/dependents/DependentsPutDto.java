package com.ifba.educampo.domain.dto.associate.dependents;

import jakarta.validation.constraints.Size;

public record DependentsPutDto(
        @Size(min = 3, message = "Spouse name must be at least 3 characters long")
        String spouse, // Conjuge
        Integer minorChildren, // Filhos menores
        Integer maleChildren, // Filhos homens
        Integer femaleChildren, // Filhas mulheres
        Integer otherDependents // Outros dependentes
) {
}
