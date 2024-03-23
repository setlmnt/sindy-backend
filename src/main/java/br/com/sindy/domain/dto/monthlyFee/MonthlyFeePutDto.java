package br.com.sindy.domain.dto.monthlyFee;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MonthlyFeePutDto(
        BigDecimal feeValue, // Valor da Mensalidade
        BigDecimal registrationValue, // Taxa de Matrícula

        LocalDate finalDate // Data de Término
) {
}
