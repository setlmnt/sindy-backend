package br.com.sindy.domain.dto.address;

import com.fasterxml.jackson.annotation.JsonInclude;

public record AddressResponseDto(
        Long id,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        String street, // Rua

        String city, // Cidade

        @JsonInclude(JsonInclude.Include.NON_NULL)
        String number, // Número

        @JsonInclude(JsonInclude.Include.NON_NULL)
        String complement, // Complemento

        String neighborhood, // Bairro

        @JsonInclude(JsonInclude.Include.NON_NULL)
        String zipCode // CEP
) {
}
