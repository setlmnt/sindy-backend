package br.com.sindy.domain.dto;

public record FileSimplifiedResponseDto(
        Long id,
        String archiveName,
        String url
) {
}
