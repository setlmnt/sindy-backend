package com.ifba.educampo.dto.monthlyFee.date;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record MonthlyFeeDateDto(
        @NotNull(message = "Month is required")
        @Min(value = 1, message = "Payment Month must be greater than 0")
        @Max(value = 12, message = "Payment Month must be less than 13")
        Integer month, // Mês de Pagamento

        @NotNull(message = "Year is required")
        @Min(value = 1900, message = "Year must be greater than 1899")
        @Max(value = 3000, message = "Year must be less than 3001")
        Integer year // Ano de Pagamento
) {
}
