package com.ifba.educampo.dto.associate;

import com.ifba.educampo.dto.address.AddressPutDto;
import com.ifba.educampo.dto.associate.affiliation.AffiliationPutDto;
import com.ifba.educampo.dto.associate.dependents.DependentsPutDto;
import com.ifba.educampo.dto.associate.placeOfBirth.PlaceOfBirthPutDto;
import com.ifba.educampo.dto.associate.workRecord.WorkRecordPutDto;
import com.ifba.educampo.enums.MaritalStatusEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record AssociatePutDto(
        @Size(min = 3, message = "Associate name must be at least 3 characters long")
        String name, // Nome

        @Positive(message = "Union card must be positive")
        Long unionCard, // Carteira Sindical

        @CPF(message = "CPF is invalid")
        String cpf, // CPF
        String rg, // RG
        String profession, // Profissão
        String workplace, // Local de Trabalho

        @Size(min = 12, max = 13, message = "Phone must be 12 or 13 characters long")
        @Pattern(regexp = "^(?:\\d{2} \\d{5}-\\d{4}|\\d{2} \\d{4}-\\d{4})$", message = "Phone must be in the format xx xxxx-xxxx or xx xxxxx-xxxx")
        String phone, // Telefone
        @Email(message = "E-mail is invalid")
        String email, // E-mail

        String nationality, // Nacionalidade

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate birthAt, // Data de Nascimento
        Boolean isLiterate, // Alfabetizado
        Boolean isVoter, // Eleitor

        @Enumerated(EnumType.STRING)
        MaritalStatusEnum maritalStatus, // Estado Civil

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate associationAt, // Data de Associação
        Long localOfficeId,

        AddressPutDto address,
        DependentsPutDto dependents,
        AffiliationPutDto affiliation,
        PlaceOfBirthPutDto placeOfBirth,
        WorkRecordPutDto workRecord
) {
}