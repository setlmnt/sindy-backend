package com.ifba.educampo.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AffiliationDto {
    private Long id;

    @NotNull(message = "Father name is required")
    @NotBlank(message = "Father name is required")
    @Size(min = 3, message = "Father name must be at least 3 characters long")
    private String fatherName; // Nome do Pai

    @NotNull(message = "Mother name is required")
    @NotBlank(message = "Mother name is required")
    @Size(min = 3, message = "Mother name must be at least 3 characters long")
    private String motherName; // Nome da Mãe
}
