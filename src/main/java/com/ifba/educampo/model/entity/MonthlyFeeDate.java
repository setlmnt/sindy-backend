package com.ifba.educampo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "monthly_fee_dates")
public class MonthlyFeeDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer month;

    @Column(nullable = false)
    private Integer year;

    @JsonIgnoreProperties("paymentDates")
    @ManyToOne
    @JoinColumn(name = "monthly_fee_id", nullable = false)
    private MonthlyFee monthlyFee;

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

    public void delete() {
        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "MonthlyFeeDate{" +
                "id=" + id +
                ", month=" + month +
                ", year=" + year +
                ", monthlyFee=" + monthlyFee +
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
        MonthlyFeeDate that = (MonthlyFeeDate) o;
        return Objects.equals(id, that.id) && Objects.equals(month, that.month) && Objects.equals(year, that.year) && Objects.equals(deleted, that.deleted) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(deletedAt, that.deletedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, month, year, deleted, createdAt, updatedAt, deletedAt);
    }
}
