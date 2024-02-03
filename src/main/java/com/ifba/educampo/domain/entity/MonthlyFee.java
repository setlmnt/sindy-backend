package com.ifba.educampo.domain.entity;

import com.ifba.educampo.domain.entity.associate.Associate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "monthly_fees")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class MonthlyFee extends BaseEntity<Long> {
    @Column(name = "fee_value", nullable = false)
    private BigDecimal feeValue; // Valor da Mensalidade

    @Column(name = "registration_value")
    private BigDecimal registrationValue; // Taxa de Matrícula

    @Column(name = "total_fee_value", nullable = false)
    private BigDecimal totalFeeValue; // Valor Total

    @Column(name = "initial_date", nullable = false)
    private LocalDate initialDate; // Data de Início

    @Column(name = "final_date", nullable = false)
    private LocalDate finalDate; // Data de Término

    @Column(name = "total_months_paid", nullable = false)
    private Long totalMonthsPaid; // Quantidade de meses pagos

    @ManyToOne
    @JoinColumn(name = "associate_id", nullable = false)
    private Associate associate; // Associado relacionado à mensalidade

    public void update(MonthlyFee monthlyFee) {
        if (monthlyFee.getFeeValue() != null) setFeeValue(monthlyFee.getFeeValue());
        if (monthlyFee.getRegistrationValue() != null) setRegistrationValue(monthlyFee.getRegistrationValue());
        if (monthlyFee.getFeeValue() != null || monthlyFee.getRegistrationValue() != null) {
            setTotalFeeValue(getFeeValue().add(getRegistrationValue() == null ? BigDecimal.valueOf(0) : getRegistrationValue()));
        }

        if (monthlyFee.getFinalDate() != null) {
            setFinalDate(monthlyFee.getFinalDate());
            setTotalMonthsPaid(ChronoUnit.MONTHS.between(getInitialDate(), getFinalDate()));
        }
    }

    public void setTotalFeeValue() {
        this.totalFeeValue = getFeeValue().add(getRegistrationValue() == null ? BigDecimal.valueOf(0) : getRegistrationValue());
    }

    public void setTotalMonthsPaid() {
        this.totalMonthsPaid = ChronoUnit.MONTHS.between(getInitialDate(), getFinalDate());
    }

    @Override
    public String toString() {
        return "MonthlyFee{" +
                ", feeValue=" + feeValue +
                ", registrationValue=" + registrationValue +
                ", totalFeeValue=" + totalFeeValue +
                ", initialDate=" + initialDate +
                ", finalDate=" + finalDate +
                ", totalMonthsPaid=" + totalMonthsPaid +
                '}';
    }
}
