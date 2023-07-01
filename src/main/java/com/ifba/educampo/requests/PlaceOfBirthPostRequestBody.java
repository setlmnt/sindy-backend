package com.ifba.educampo.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PlaceOfBirthPostRequestBody {
	@NotNull(message = "City is required")
	@NotBlank(message = "City is required")
	private String city; // Cidade

	@NotNull(message = "State is required")
	@NotBlank(message = "State is required")
	private String state; // Estado
}
