package com.ifba.educampo.dto.associate.address;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddressPutDto(
        @Size(min = 3, message = "Street name must be at least 3 characters long")
        String street, // Rua

        @Size(min = 3, message = "City name must be at least 3 characters long")
        String city, // Cidade

        String number, // Número

        @Size(min = 3, message = "State name must be at least 3 characters long")
        String complement, // Complemento

        @Size(min = 3, message = "Neighborhood name must be at least 3 characters long")
        String neighborhood, // Bairro

        @Pattern(regexp = "(^\\d{5}-\\d{3}$)", message = "Zip code must be in the format xxxxx-xxx")
        String zipCode // CEP
) {
}
