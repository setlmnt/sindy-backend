package com.ifba.educampo.dto.associate;

import com.ifba.educampo.dto.associate.address.AddressPostDto;
import com.ifba.educampo.dto.associate.affiliation.AffiliationPostDto;
import com.ifba.educampo.dto.associate.dependents.DependentsPostDto;
import com.ifba.educampo.dto.associate.placeOfBirth.PlaceOfBirthPostDto;
import com.ifba.educampo.dto.associate.workRecord.WorkRecordPostDto;
import com.ifba.educampo.model.enums.MaritalStatus;
import jakarta.annotation.Nullable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record AssociatePostDto(
        @NotNull(message = "Associate Name is required")
        @NotBlank(message = "Associate name is required")
        @Size(min = 3, message = "Associate name must be at least 3 characters long")
        String name, // Nome

        @NotNull(message = "Union card is required")
        Long unionCard, // Carteira Sindical

        @NotNull(message = "CPF is required")
        @NotBlank(message = "CPF is required")
        @CPF(message = "CPF is invalid")
        String cpf, // CPF

        @NotNull(message = "RG is required")
        @NotBlank(message = "RG is required")
        String rg, // RG

        @NotNull(message = "Profession is required")
        @NotBlank(message = "Profession is required")
        String profession, // Profissão

        @NotNull(message = "Workplace is required")
        @NotBlank(message = "Workplace is required")
        String workplace, // Local de Trabalho

        @NotNull(message = "Phone is required")
        @NotBlank(message = "Phone is required")
        @Size(min = 12, max = 13, message = "Phone must be 12 or 13 characters long")
        @Pattern(regexp = "^(?:\\d{2} \\d{5}-\\d{4}|\\d{2} \\d{4}-\\d{4})$", message = "Phone must be in the format xx xxxx-xxxx or xx xxxxx-xxxx")
        String phone, // Telefone

        @NotNull(message = "Nationality is required")
        @NotBlank(message = "Nationality is required")
        String nationality, // Nacionalidade

        @NotNull
        LocalDate birthAt, // Data de Nascimento

        @NotNull
        Boolean isLiterate, // Alfabetizado

        @NotNull
        Boolean isVoter, // Eleitor

        @NotNull
        @Enumerated(EnumType.STRING)
        MaritalStatus maritalStatus, // Estado Civil

        @NotNull
        LocalDate associationAt, // Data de Associação

        Long localOfficeId,

        AddressPostDto address,
        DependentsPostDto dependents,
        AffiliationPostDto affiliation,
        PlaceOfBirthPostDto placeOfBirth,
        WorkRecordPostDto workRecord
) {
}
