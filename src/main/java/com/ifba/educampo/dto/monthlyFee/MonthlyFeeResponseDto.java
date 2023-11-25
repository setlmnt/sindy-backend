package com.ifba.educampo.dto.monthlyFee;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ifba.educampo.dto.associate.AssociateResponseDto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MonthlyFeeResponseDto(
        Long id,
        BigDecimal feeValue, // Valor da Mensalidade

        @JsonInclude(JsonInclude.Include.NON_NULL)
        BigDecimal registrationValue, // Taxa de Matrícula
        BigDecimal totalFeeValue, // Valor Total

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate initialDate, // Data de Início

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate finalDate, // Data de Término
        Long totalMonthsPaid, // Quantidade de meses pagos

        @JsonIgnoreProperties({"localOffice", "address", "dependents", "affiliation", "placeOfBirth", "associatePhoto", "workRecord"})
        AssociateResponseDto associate, // Associado relacionado à mensalidade

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate createdAt // Data de Criação
) {
}
