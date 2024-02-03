package br.com.sindy.domain.dto.associate.affiliation;

public record AffiliationResponseDto(
        Long id,
        String fatherName, // Nome do Pai
        String motherName // Nome da Mãe
) {
}
