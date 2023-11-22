package com.ifba.educampo.model.entity.associate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "work_records")
public class WorkRecord { // Carteira de Trabalho
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long number; // Número

    @Column(nullable = false)
    private String series; // Série

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

    public void update(WorkRecord workRecord) {
        if (workRecord.getNumber() != null) setNumber(workRecord.getNumber());
        if (workRecord.getSeries() != null) setSeries(workRecord.getSeries());
    }

    public void delete() {
        deleted = true;
        deletedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "WorkRecord{" +
                "id=" + id +
                ", number=" + number +
                ", series='" + series + '\'' +
                ", deleted=" + deleted +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                '}';
    }
}
