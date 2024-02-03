package com.ifba.educampo.domain.dto.associate.dependents;

import com.fasterxml.jackson.annotation.JsonInclude;

public record DependentsResponseDto(
        Long id,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        String spouse, // Conjuge
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Integer minorChildren, // Filhos menores
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Integer maleChildren, // Filhos homens
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Integer femaleChildren, // Filhas mulheres
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Integer otherDependents // Outros dependentes
) {
}
