package com.ifba.educampo.model.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddressDto {
    private Long id;

    @Size(min = 3, message = "Street name must be at least 3 characters long")
    private String street; // Rua

    @Size(min = 3, message = "City name must be at least 3 characters long")
    private String city; // Cidade

    private int number; // Número

    @Size(min = 3, message = "State name must be at least 3 characters long")
    private String complement; // Complemento

    @Size(min = 3, message = "Neighborhood name must be at least 3 characters long")
    private String neighborhood; // Bairro

    @Pattern(regexp = "(^\\d{5}-\\d{3}$)", message = "Zip code must be in the format xxxxx-xxx")
    private String zipCode; // CEP
}
