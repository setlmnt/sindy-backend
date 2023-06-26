package com.ifba.educampo.requests;

import java.sql.Date;

import com.ifba.educampo.domain.MaritalStatus;

import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class AssociatePostRequestBody {
	@NotNull(message = "Associate Name is required")
	@NotBlank(message = "Associate name is required")
	@Size(min = 3, message = "Associate name must be at least 3 characters long")
	private String name; // Nome

	@NotNull(message = "Union card is required")
	private Long unionCard; // Carteira Sindical

	@NotNull(message = "CPF is required")
	@NotBlank(message = "CPF is required")
	@Size(min = 14, max = 14, message = "CPF must be 14 characters long")
	@Pattern(regexp = "^(?!(\\d)\\1{10})\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "CPF must be in the format xxx.xxx.xxx-xx")
	private String cpf; // CPF

	@NotNull(message = "RG is required")
	@NotBlank(message = "RG is required")
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

	private AddressPutRequestBody address;
	private DependentsPutRequestBody dependents;
	private AffiliationPutRequestBody affiliation;
	private PlaceOfBirthPutRequestBody placeOfBirth;
	private AssociatePhotoPutRequestBody associatePhoto;
	private WorkRecordPutRequestBody workRecord;

	@Nullable
	private Long localOfficeId;
}
