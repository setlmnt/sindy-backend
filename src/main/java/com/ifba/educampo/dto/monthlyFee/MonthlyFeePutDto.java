package com.ifba.educampo.dto.monthlyFee;

import com.ifba.educampo.dto.monthlyFee.date.MonthlyFeeDateDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;
import java.util.Set;

public record MonthlyFeePutDto(
        @Min(value = 0, message = "Monthly Fee Value must be greater than 0")
        BigDecimal feeValue, // Valor da Mensalidade
        BigDecimal totalAmount, // Valor Pago
        @Valid
        Set<MonthlyFeeDateDto> paymentDates // Datas de Pagamento
) {
}
