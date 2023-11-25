package com.ifba.educampo.dto.monthlyFee;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MonthlyFeePostDto(
        @NotNull(message = "Monthly Fee Value is required")
        @Min(value = 0, message = "Monthly Fee Value must be greater than 0")
        BigDecimal feeValue, // Valor da Mensalidade

        @Min(value = 0, message = "Registration Value must be greater than 0")
        BigDecimal registrationValue, // Taxa de Matrícula

        @NotNull
        LocalDate initialDate, // Data de Início

        @NotNull
        LocalDate finalDate, // Data de Término

        @NotNull(message = "Associate Id is required")
        Long associateId // Associado relacionado à mensalidade
) {
}
