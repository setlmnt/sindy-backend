package br.com.sindy.domain.dto.associate.placeOfBirth;

public record PlaceOfBirthResponseDto(
        Long id,
        String city, // Cidade
        String state // Estado
) {
}
