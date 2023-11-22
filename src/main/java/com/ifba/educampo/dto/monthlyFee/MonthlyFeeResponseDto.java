package com.ifba.educampo.dto.monthlyFee;

import com.ifba.educampo.dto.monthlyFee.date.MonthlyFeeDateResponseDto;

import java.math.BigDecimal;
import java.util.Set;

public record MonthlyFeeResponseDto(
        Long id,
        BigDecimal feeValue, // Valor da Mensalidade
        BigDecimal totalAmount, // Valor Pago
        Integer totalMonthsPaid, // Quantidade de meses pagos
        Set<MonthlyFeeDateResponseDto> paymentDates, // Datas de Pagamento
        Long associateId // Associado relacionado à mensalidade
) {
}
