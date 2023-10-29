package com.ifba.educampo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DependentsDto {
    private Long id;

    @NotNull(message = "Wife name is required")
    @NotBlank(message = "Wife name is required")
    @Size(min = 3, message = "Wife name must be at least 3 characters long")
    private String wifeName; // Nome da Esposa

    @NotNull(message = "Minor children is required")
    private int minorChildren; // Filhos menores

    @NotNull(message = "Male children is required")
    private int maleChildren; // Filhos homens

    @NotNull(message = "Famale children is required")
    private int femaleChildren; // Filhas mulheres

    @NotNull(message = "Other dependents is required")
    private int otherDependents; // Outros dependentes
}
