package com.ifba.educampo.model.dto;

import lombok.Data;

@Data
public class AssociatePhotoDto {
    private Long id;
    private String archiveName; // Nome do Arquivo
    private String originalName; // Nome Original
    private String contentType; // Tipo de Conteúdo
    private Long size; // Tamanho
    private String url; // URL
}
