package com.ifba.educampo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class MonthlyFeeDto {
    private Long id;

    @NotNull(message = "Monthly Fee Value is required")
    @Min(value = 0, message = "Monthly Fee Value must be greater than 0")
    private BigDecimal feeValue; // Valor da Mensalidade

    private BigDecimal totalAmount; // Valor Pago

    private Integer totalMonthsPaid; // Quantidade de meses pagos

    @NotEmpty(message = "Payment Dates are required")
    @Valid
    private List<MonthlyFeeDateDto> paymentDates; // Datas de Pagamento

    @NotNull(message = "Associate Id is required")
    private Long associateId; // Associado relacionado à mensalidade
}
