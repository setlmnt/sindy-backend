package com.ifba.educampo.dto.associate.address;

public record AddressResponseDto(
        Long id,
        String street, // Rua
        String city, // Cidade
        String number, // Número
        String complement, // Complemento
        String neighborhood, // Bairro
        String zipCode // CEP
) {
}
