package com.ifba.educampo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ifba.educampo.model.entity.associate.Associate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

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

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount; // Valor Total

    @Column(name = "total_months_paid", nullable = false)
    private Integer totalMonthsPaid; // Quantidade de meses pagos

    @JsonIgnoreProperties("monthlyFee")
    @OneToMany(mappedBy = "monthlyFee", cascade = CascadeType.ALL)
    private Set<MonthlyFeeDate> paymentDates; // Datas de Pagamento

    @JsonInclude(JsonInclude.Include.CUSTOM)
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
    private LocalDateTime deletedAt; // Data de Atualização

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
        if (monthlyFee.getTotalAmount() != null) setTotalAmount(monthlyFee.getTotalAmount());
        if (monthlyFee.getTotalMonthsPaid() != null) setTotalMonthsPaid(monthlyFee.getTotalMonthsPaid());
        if (monthlyFee.getPaymentDates() != null) {
            this.paymentDates.forEach(MonthlyFeeDate::delete);
            setPaymentDates(monthlyFee.getPaymentDates());
        }
    }

    public void delete() {
        this.deleted = true;
        this.deletedAt = LocalDateTime.now();

        this.paymentDates.forEach(MonthlyFeeDate::delete);
    }

    @Override
    public String toString() {
        return "MonthlyFee{" +
                "id=" + id +
                ", feeValue=" + feeValue +
                ", totalAmount=" + totalAmount +
                ", deleted=" + deleted +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonthlyFee that = (MonthlyFee) o;
        return Objects.equals(id, that.id) && Objects.equals(feeValue, that.feeValue) && Objects.equals(totalAmount, that.totalAmount) && Objects.equals(totalMonthsPaid, that.totalMonthsPaid) && Objects.equals(deleted, that.deleted) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(deletedAt, that.deletedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, feeValue, totalAmount, totalMonthsPaid, deleted, createdAt, updatedAt, deletedAt);
    }
}
