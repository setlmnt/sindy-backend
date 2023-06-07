package com.ifba.educampo.domain;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "addresses")
public class Address { // Endereço
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(min = 3, message = "Street name must be at least 3 characters long")
	private String street; // Rua

	@Size(min = 3, message = "City name must be at least 3 characters long")
	private String city; // Cidade

	@Size(min = 1, message = "State name must be at least 1 characters long")
	private int number; // Número

	@Size(min = 3, message = "State name must be at least 3 characters long")
	private String complement; // Complemento

	@Size(min = 3, message = "Neighborhood name must be at least 3 characters long")
	private String neighborhood; // Bairro

	@Pattern(regexp = "^\\d{5}\\d{3}$", message = "Zip code must be in the format xxxxxxxx")
	private String zipCode; // CEP

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdAt", nullable = false, updatable = false)
	private java.util.Date createdAt; // Data de criação

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatedAt", nullable = false)
	private java.util.Date updatedAt; // Data de atualização

	@PrePersist
	protected void onCreate() {
		createdAt = new java.util.Date();
		updatedAt = new java.util.Date();
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = new java.util.Date();
	}
}
