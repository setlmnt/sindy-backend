package com.ifba.educampo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WorkRecordDto {
    private Long id;

    @NotNull(message = "Number is required")
    private Long number; // Número

    @NotNull(message = "Series is required")
    @NotBlank(message = "Series is required")
    private String series; // Série
}
