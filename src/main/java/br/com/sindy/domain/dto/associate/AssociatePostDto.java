package br.com.sindy.domain.dto.associate;

import br.com.sindy.domain.dto.address.AddressPostDto;
import br.com.sindy.domain.dto.associate.affiliation.AffiliationPostDto;
import br.com.sindy.domain.dto.associate.dependents.DependentsPostDto;
import br.com.sindy.domain.dto.associate.placeOfBirth.PlaceOfBirthPostDto;
import br.com.sindy.domain.dto.associate.workRecord.WorkRecordPostDto;
import br.com.sindy.domain.enums.MaritalStatusEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record AssociatePostDto(
        @NotBlank(message = "Associate name is required")
        @Size(min = 3, message = "Associate name must be at least 3 characters long")
        String name, // Nome

        @NotNull(message = "Union card is required")
        @Positive(message = "Union card must be positive")
        Long unionCard, // Carteira Sindical

        @NotBlank(message = "CPF is required")
        @CPF(message = "CPF is invalid")
        String cpf, // CPF

        @NotBlank(message = "RG is required")
        String rg, // RG

        @NotBlank(message = "Profession is required")
        String profession, // Profissão

        @NotBlank(message = "Workplace is required")
        String workplace, // Local de Trabalho

        @Size(min = 12, max = 13, message = "Phone must be 12 or 13 characters long")
        @Pattern(regexp = "^(?:\\d{2} \\d{5}-\\d{4}|\\d{2} \\d{4}-\\d{4})$", message = "Phone must be in the format xx xxxx-xxxx or xx xxxxx-xxxx")
        String phone, // Telefone

        @Email(message = "E-mail is invalid")
        String email, // E-mail

        @NotBlank(message = "Nationality is required")
        String nationality, // Nacionalidade

        @NotNull(message = "Birth at is required")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate birthAt, // Data de Nascimento

        @NotNull
        Boolean isLiterate, // Alfabetizado

        @NotNull
        Boolean isVoter, // Eleitor

        @NotNull
        @Enumerated(EnumType.STRING)
        MaritalStatusEnum maritalStatus, // Estado Civil

        @NotNull(message = "Association at is required")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate associationAt, // Data de Associação

        Long localOfficeId,

        AddressPostDto address,
        DependentsPostDto dependents,
        AffiliationPostDto affiliation,
        PlaceOfBirthPostDto placeOfBirth,
        WorkRecordPostDto workRecord
) {
}
