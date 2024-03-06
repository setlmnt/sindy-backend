package br.com.sindy.domain.dto.monthlyFee;

import br.com.sindy.domain.dto.associate.AssociateSimplifiedResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MonthlyFeeResponseDto(
        Long id,
        BigDecimal feeValue,
        BigDecimal registrationValue,
        BigDecimal totalFeeValue,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate initialDate,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate finalDate,
        Long totalMonthsPaid,
        AssociateSimplifiedResponseDto associate,
        @JsonFormat(pattern = "yyyy-MM-dd")
        OffsetDateTime createdAt
) {
}
