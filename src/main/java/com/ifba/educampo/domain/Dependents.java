package com.ifba.educampo.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@Table(name = "dependents")
public class Dependents { // Dependentes
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
