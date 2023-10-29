package com.ifba.educampo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LocalOfficeDto {
    private Long id;

    @NotNull(message = "Local Office Name is required")
    @NotBlank(message = "Local Office Name is required")
    @Size(min = 3, message = "Local Office Name must be at least 3 characters long")
    private String name; // Nome
}
