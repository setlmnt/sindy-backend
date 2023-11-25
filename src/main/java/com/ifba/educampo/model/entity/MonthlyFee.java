package com.ifba.educampo.model.entity;

import com.ifba.educampo.model.entity.associate.Associate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "monthly_fees")
public class MonthlyFee { // Mensalidade
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(nullable = false)
    private Boolean deleted = false;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // Data de Criação

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // Data de Atualização

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt; // Data de Exclusão

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

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

    public void delete() {
        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
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
                "id=" + id +
                ", feeValue=" + feeValue +
                ", registrationValue=" + registrationValue +
                ", totalFeeValue=" + totalFeeValue +
                ", initialDate=" + initialDate +
                ", finalDate=" + finalDate +
                ", totalMonthsPaid=" + totalMonthsPaid +
                ", deleted=" + deleted +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                '}';
    }
}
