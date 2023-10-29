package com.ifba.educampo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dependents")
public class Dependents { // Dependentes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "wife_name", nullable = false)
    private String wifeName; // Nome da Esposa

    @Column(name = "minor_children", nullable = false)
    private int minorChildren; // Filhos menores

    @Column(name = "male_children", nullable = false)
    private int maleChildren; // Filhos homens

    @Column(name = "female_children", nullable = false)
    private int femaleChildren; // Filhas mulheres

    @Column(name = "other_dependents", nullable = false)
    private int otherDependents; // Outros dependentes

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
        return "Dependents{" +
                "id=" + id +
                ", wifeName='" + wifeName + '\'' +
                ", minorChildren=" + minorChildren +
                ", maleChildren=" + maleChildren +
                ", femaleChildren=" + femaleChildren +
                ", otherDependents=" + otherDependents +
                ", deleted=" + deleted +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                '}';
    }
}
