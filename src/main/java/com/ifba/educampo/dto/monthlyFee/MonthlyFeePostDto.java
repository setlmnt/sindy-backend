package com.ifba.educampo.dto.monthlyFee;

import com.ifba.educampo.dto.monthlyFee.date.MonthlyFeeDateDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Set;

public record MonthlyFeePostDto(
        @NotNull(message = "Monthly Fee Value is required")
        @Min(value = 0, message = "Monthly Fee Value must be greater than 0")
        BigDecimal feeValue, // Valor da Mensalidade

        BigDecimal totalAmount, // Valor Pago

        @NotEmpty(message = "Payment Dates are required")
        @Valid
        Set<MonthlyFeeDateDto> paymentDates, // Datas de Pagamento

        @NotNull(message = "Associate Id is required")
        Long associateId // Associado relacionado à mensalidade
) {
}
