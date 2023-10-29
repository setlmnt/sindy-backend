package com.ifba.educampo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
    private List<MonthlyFeeDate> paymentDates; // Datas de Pagamento

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

    public void delete() {
        this.deleted = true;
        this.deletedAt = LocalDateTime.now();

        this.paymentDates.forEach(MonthlyFeeDate::delete);
    }
}
