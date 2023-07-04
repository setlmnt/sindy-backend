package com.ifba.educampo.domain;

import java.sql.Date;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
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
	@Column(unique = true)
	private Long unionCard; // Carteira Sindical

	@NotNull(message = "CPF is required")
	@NotBlank(message = "CPF is required")
	@Column(unique = true)
	@Size(min = 14, max = 14, message = "CPF must be 14 characters long")
	@Pattern(regexp = "^(?!(\\d)\\1{10})\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "CPF must be in the format xxx.xxx.xxx-xx")
	private String cpf; // CPF

	@NotNull(message = "RG is required")
	@NotBlank(message = "RG is required")
	@Column(unique = true)
	private String rg; // RG

	@NotNull(message = "Profession is required")
	@NotBlank(message = "Profession is required")
	private String profession; // Profissão

	@NotNull(message = "Workplace is required")
	@NotBlank(message = "Workplace is required")
	private String workplace; // Local de Trabalho

	@NotNull(message = "Phone is required")
	@NotBlank(message = "Phone is required")
	@Size(min = 12, max = 13, message = "Phone must be 12 or 13 characters long")
	@Pattern(regexp = "^(?:\\d{2} \\d{5}-\\d{4}|\\d{2} \\d{4}-\\d{4})$", message = "Phone must be in the format xx xxxx-xxxx or xx xxxxx-xxxx")
	private String phone; // Telefone

	@NotNull(message = "Nationality is required")
	@NotBlank(message = "Nationality is required")
	private String nationality; // Nacionalidade

	@NotNull(message = "Birth Date is required")
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date birthDate; // Data de Nascimento

	@NotNull(message = "Is Literate is required")
	private boolean isLiterate; // Alfabetizado

	@NotNull(message = "Is Voter is required")
	private boolean isVoter; // Eleitor

	@NotNull(message = "Marital Status is required")
	@Enumerated(EnumType.STRING)
	private MaritalStatus maritalStatus; // Estado Civil

	@NotNull(message = "Associate Date is required")
	private java.util.Date associationDate; // Data de Associação


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdAt", nullable = false, updatable = false)
	private java.util.Date createdAt; // Data de Criação

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatedAt", nullable = false)
	private java.util.Date updatedAt; // Data de Atualização


	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "localOfficeId", nullable = true)
	private LocalOffice localOffice; // Delegacia (Escritório Local)

	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "addressId", nullable = true)
	private Address address; // Endereço

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "dependentsId", nullable = false)
	private Dependents dependents; // Dependentes

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "affiliationId", nullable = false)
	private Affiliation affiliation; // Filiação

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "placeOfBirthId", nullable = false)
	private PlaceOfBirth placeOfBirth; // Naturalidade

	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "associatePhotoId", nullable = false)
	private AssociatePhoto associatePhoto; // Foto do Associado

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "workRecordId", nullable = false)
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
