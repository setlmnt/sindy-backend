package com.ifba.educampo.dto.associate.dependents;

import com.fasterxml.jackson.annotation.JsonInclude;

public record DependentsResponseDto(
        Long id,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        String spouse, // Conjuge
        Integer minorChildren, // Filhos menores
        Integer maleChildren, // Filhos homens
        Integer femaleChildren, // Filhas mulheres
        Integer otherDependents // Outros dependentes
) {
}
