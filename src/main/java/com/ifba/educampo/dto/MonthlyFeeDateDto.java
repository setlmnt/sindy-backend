package com.ifba.educampo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class MonthlyFeeDateDto {
    @NotNull(message = "Month is required")
    @Min(value = 1, message = "Payment Month must be greater than 0")
    @Max(value = 12, message = "Payment Month must be less than 13")
    private Integer month; // Mês de Pagamento

    @NotNull(message = "Year is required")
    @Min(value = 1000, message = "Year must be greater than 999")
    @Max(value = 9999, message = "Year must be less than 10000")
    private Integer year; // Ano de Pagamento

    @JsonIgnore
    private Long monthlyFeeId;
}
