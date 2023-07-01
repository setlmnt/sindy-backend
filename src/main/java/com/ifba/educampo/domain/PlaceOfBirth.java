package com.ifba.educampo.domain;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "placesOfBirth")
public class PlaceOfBirth { // Local de Nascimento
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "City is required")
	@NotBlank(message = "City is required")
	private String city; // Cidade

	@NotNull(message = "State is required")
	@NotBlank(message = "State is required")
	private String state; // Estado

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
