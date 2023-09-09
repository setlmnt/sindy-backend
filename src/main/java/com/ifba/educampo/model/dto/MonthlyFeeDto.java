package com.ifba.educampo.model.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class MonthlyFeeDto {
    private Long id;

    @NotNull(message = "Monthly Fee Value is required")
    @Min(value = 0, message = "Monthly Fee Value must be greater than 0")
    private float paidAmount; // Valor Pago

    @NotNull(message = "Payment Month is required")
    @Min(value = 1, message = "Payment Month must be greater than 0")
    @Max(value = 12, message = "Payment Month must be less than 13")
    private Integer paymentMonth; // Mês de Pagamento

    @NotNull(message = "Payment Year is required")
    private Integer paymentYear; // Ano de Pagamento

    @NotNull(message = "Associate Id is required")
    private Long associateId; // Associado relacionado à mensalidade
}
