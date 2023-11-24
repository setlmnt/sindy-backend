package com.ifba.educampo.dto.associate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ifba.educampo.dto.ImageResponseDto;
import com.ifba.educampo.dto.associate.address.AddressResponseDto;
import com.ifba.educampo.dto.associate.affiliation.AffiliationResponseDto;
import com.ifba.educampo.dto.associate.dependents.DependentsResponseDto;
import com.ifba.educampo.dto.associate.placeOfBirth.PlaceOfBirthResponseDto;
import com.ifba.educampo.dto.associate.workRecord.WorkRecordResponseDto;
import com.ifba.educampo.dto.localOffice.LocalOfficeResponseDto;
import com.ifba.educampo.model.enums.MaritalStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AssociateResponseDto(
        Long id,
        String name, // Nome
        Long unionCard, // Carteira Sindical
        String cpf, // CPF
        String rg, // RG
        String profession, // Profissão
        String workplace, // Local de Trabalho
        String phone, // Telefone
        String nationality, // Nacionalidade

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate birthAt, // Data de Nascimento
        Boolean isLiterate, // Alfabetizado
        Boolean isVoter, // Eleitor
        MaritalStatus maritalStatus, // Estado Civil

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate associationAt, // Data de Associação
        Boolean deleted,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        LocalOfficeResponseDto localOffice,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        AddressResponseDto address,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        DependentsResponseDto dependents,
        AffiliationResponseDto affiliation,
        PlaceOfBirthResponseDto placeOfBirth,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        ImageResponseDto associatePhoto,
        WorkRecordResponseDto workRecord
) {
}
