package br.com.sindy.domain.dto.associate;

import br.com.sindy.domain.dto.FileResponseDto;
import br.com.sindy.domain.dto.address.AddressResponseDto;
import br.com.sindy.domain.dto.associate.affiliation.AffiliationResponseDto;
import br.com.sindy.domain.dto.associate.dependents.DependentsResponseDto;
import br.com.sindy.domain.dto.associate.placeOfBirth.PlaceOfBirthResponseDto;
import br.com.sindy.domain.dto.associate.workRecord.WorkRecordResponseDto;
import br.com.sindy.domain.dto.localOffice.LocalOfficeResponseDto;
import br.com.sindy.domain.enums.MaritalStatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

public record AssociateResponseDto(
        Long id,
        String name, // Nome
        Long unionCard, // Carteira Sindical
        String cpf, // CPF
        String rg, // RG
        String profession, // Profissão
        String workplace, // Local de Trabalho
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String phone, // Telefone
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String email, // E-mail
        String nationality, // Nacionalidade

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate birthAt, // Data de Nascimento
        Boolean isLiterate, // Alfabetizado
        Boolean isVoter, // Eleitor
        Boolean isPaid, // Pagou a Mensalidade
        MaritalStatusEnum maritalStatus, // Estado Civil

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
        FileResponseDto associatePhoto,
        WorkRecordResponseDto workRecord
) {
}
