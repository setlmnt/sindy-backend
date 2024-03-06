package br.com.sindy.domain.dto;

public record FileResponseDto(
        Long id,
        String archiveName,
        String originalName,
        String contentType,
        Long size,
        String url
) {
}
