package com.ifba.educampo.dto.syndicate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ifba.educampo.dto.address.AddressResponseDto;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SyndicateResponseDto(
        Long id,

        String name, // Nome

        String cnpj, // CNPJ

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate foundationDate, // Data de Fundação

        String email, // E-mail

        String phone, // Telefone

        AddressResponseDto address // Endereço
) {
}
