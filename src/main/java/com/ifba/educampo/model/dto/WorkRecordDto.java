package com.ifba.educampo.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class WorkRecordDto {
    private Long id;

    @NotNull(message = "Number is required")
    private Long number; // Número

    @NotNull(message = "Series is required")
    @NotBlank(message = "Series is required")
    private String series; // Série
}
