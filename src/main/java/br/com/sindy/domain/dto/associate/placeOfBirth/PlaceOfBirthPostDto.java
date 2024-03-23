package br.com.sindy.domain.dto.associate.placeOfBirth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PlaceOfBirthPostDto(
        @NotNull(message = "City is required")
        @NotBlank(message = "City is required")
        String city, // Cidade

        @NotNull(message = "State is required")
        @NotBlank(message = "State is required")
        String state // Estado
) {
}
