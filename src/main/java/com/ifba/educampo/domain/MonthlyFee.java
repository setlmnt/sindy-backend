package com.ifba.educampo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "monthly_fees", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"associateId", "paymentMonth", "paymentYear"})
})
public class MonthlyFee { // Mensalidade
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @JsonInclude(JsonInclude.Include.CUSTOM)
    @ManyToOne
    @JoinColumn(name = "associateId", nullable = false)
    private Associate associate; // Associado relacionado à mensalidade

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdAt", nullable = false, updatable = false)
    private java.util.Date createdAt; // Data de Criação

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatedAt", nullable = false)
    private java.util.Date updatedAt; // Data de Atualização

    @PrePersist
    protected void onCreate() {
        createdAt = new java.util.Date();
        updatedAt = new java.util.Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new java.util.Date();
    }
}
