package br.com.sindy.domain.dto.associate.workRecord;

public record WorkRecordResponseDto(
        Long id,
        Long number, // Número
        String series // Série
) {
}
