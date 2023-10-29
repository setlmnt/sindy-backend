package com.ifba.educampo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PlaceOfBirthDto {
    private Long id;

    @NotNull(message = "City is required")
    @NotBlank(message = "City is required")
    private String city; // Cidade

    @NotNull(message = "State is required")
    @NotBlank(message = "State is required")
    private String state; // Estado
}
