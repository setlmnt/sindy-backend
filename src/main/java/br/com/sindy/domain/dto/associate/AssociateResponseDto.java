package br.com.sindy.domain.dto.associate;

import br.com.sindy.domain.dto.FileSimplifiedResponseDto;
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

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AssociateResponseDto(
        Long id,
        String name,
        Long unionCard,
        String cpf,
        String rg,
        String profession,
        String workplace,
        String phone,
        String email,
        String nationality,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate birthAt,
        Boolean isLiterate,
        Boolean isVoter,
        Boolean isPaid,
        MaritalStatusEnum maritalStatus,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate associationAt,
        Boolean deleted,
        LocalOfficeResponseDto localOffice,
        AddressResponseDto address,
        DependentsResponseDto dependents,
        AffiliationResponseDto affiliation,
        PlaceOfBirthResponseDto placeOfBirth,
        FileSimplifiedResponseDto profilePicture,
        WorkRecordResponseDto workRecord
) {
}
