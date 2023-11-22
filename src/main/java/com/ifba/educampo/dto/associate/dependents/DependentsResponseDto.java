package com.ifba.educampo.dto.associate.dependents;

public record DependentsResponseDto(
        Long id,
        String spouse, // Conjuge
        Integer minorChildren, // Filhos menores
        Integer maleChildren, // Filhos homens
        Integer femaleChildren, // Filhas mulheres
        Integer otherDependents // Outros dependentes
) {
}
