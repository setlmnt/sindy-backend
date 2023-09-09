package com.ifba.educampo.model.dto;

import lombok.Data;

@Data
public class AssociatePhotoDto {
    private Long id;
    private String archiveName;
    private String contentType;
    private Long size;
}
