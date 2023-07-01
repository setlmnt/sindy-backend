package com.ifba.educampo.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class DependentsPutRequestBody {
	private Long id;

	@NotNull(message = "Wife name is required")
	@NotBlank(message = "Wife name is required")
	@Size(min = 3, message = "Wife name must be at least 3 characters long")
	private String wifeName; // Nome da Esposa

	@NotNull(message = "Minor children is required")
	private int minorChildren; // Filhos menores

	@NotNull(message = "Male children is required")
	private int maleChildren; // Filhos homens

	@NotNull(message = "Famale children is required")
	private int femaleChildren; // Filhas mulheres

	@NotNull(message = "Other dependents is required")
	private int otherDependents; // Outros dependentes
}
