package com.ifba.educampo.domain;

import java.sql.Date;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "associates")
public class Associate { // Associado
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Associate Name is required")
	@NotBlank(message = "Associate name is required")
	@Size(min = 3, message = "Associate name must be at least 3 characters long")
	private String name; // Nome

	@NotNull(message = "Union card is required")
	@NotBlank(message = "Union card is required")
	@Column(unique = true)
	private Long unionCard; // Carteira Sindical

	@NotNull(message = "CPF is required")
	@NotBlank(message = "CPF is required")
	@Column(unique = true)
	@Size(max = 11, message = "CPF must be 11 characters long")
	private Long cpf; // CPF

	@NotNull(message = "RG is required")
	@NotBlank(message = "RG is required")
	@Column(unique = true)
	private Long rg; // RG

	@NotNull(message = "Profession is required")
	@NotBlank(message = "Profession is required")
	private String profession; // Profissão

	@NotNull(message = "Workplace is required")
	@NotBlank(message = "Workplace is required")
	private String workplace; // Local de Trabalho

	@NotNull(message = "Phone is required")
	@NotBlank(message = "Phone is required")
	@Pattern(regexp = "^\\d{11}$", message = "Phone must be in the format xxxxxxxxxxx")
	private String phone; // Telefone

	@NotNull(message = "Nationality is required")
	@NotBlank(message = "Nationality is required")
	private String nationality; // Nacionalidade

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date birthDate; // Data de Nascimento

	@NotNull
	private boolean isLiterate; // Alfabetizado

	@NotNull
	private boolean isVoter; // Eleitor

	@NotNull
	@Enumerated(EnumType.STRING)
	private MaritalStatus maritalStatus; // Estado Civil

	@NotNull
	private java.util.Date associationDate; // Data de Associação


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdAt", nullable = false, updatable = false)
	private java.util.Date createdAt; // Data de Criação

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatedAt", nullable = false)
	private java.util.Date updatedAt; // Data de Atualização


	@Nullable
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "addressId")
	private Address address; // Endereço

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "dependentsId")
	private Dependents dependents; // Dependentes

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "affiliationId")
	private Affiliation affiliation; // Filiação

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "placeOfBirthId")
	private PlaceOfBirth placeOfBirth; // Naturalidade

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "associatePhotoId")
	private AssociatePhoto associatePhoto; // Foto do Associado

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "workRecordId")
	private WorkRecord workRecord; // Carteira de Trabalho

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
