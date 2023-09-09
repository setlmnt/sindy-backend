package com.ifba.educampo.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "monthly_fees", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"associateId", "paymentMonth", "paymentYear"})
})
public class MonthlyFee { // Mensalidade
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private float paidAmount; // Valor Pago

    @Column(nullable = false)
    private Integer paymentMonth; // Mês de Pagamento

    @Column(nullable = false)
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
