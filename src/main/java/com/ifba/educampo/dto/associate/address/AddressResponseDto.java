package com.ifba.educampo.dto.associate.address;

import com.fasterxml.jackson.annotation.JsonInclude;

public record AddressResponseDto(
        Long id,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        String street, // Rua

        @JsonInclude(JsonInclude.Include.NON_NULL)
        String city, // Cidade

        @JsonInclude(JsonInclude.Include.NON_NULL)
        String number, // Número

        @JsonInclude(JsonInclude.Include.NON_NULL)
        String complement, // Complemento

        @JsonInclude(JsonInclude.Include.NON_NULL)
        String neighborhood, // Bairro

        @JsonInclude(JsonInclude.Include.NON_NULL)
        String zipCode // CEP
) {
}
