package com.ifba.educampo.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LocalOfficeDto {
    private Long id;

    @NotNull(message = "Local Office Name is required")
    @NotBlank(message = "Local Office Name is required")
    @Size(min = 3, message = "Local Office Name must be at least 3 characters long")
    private String name; // Nome
}
