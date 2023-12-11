package com.ifba.educampo.entity.associate;

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

    private String spouse; // Nome da Esposa

    @Column(name = "minor_children")
    private int minorChildren; // Filhos menores

    @Column(name = "male_children")
    private int maleChildren; // Filhos homens

    @Column(name = "female_children")
    private int femaleChildren; // Filhas mulheres

    @Column(name = "other_dependents")
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

    public void update(Dependents dependents) {
        if (dependents.getSpouse() != null) setSpouse(dependents.getSpouse());
        if (dependents.getMinorChildren() != 0) setMinorChildren(dependents.getMinorChildren());
        if (dependents.getMaleChildren() != 0) setMaleChildren(dependents.getMaleChildren());
        if (dependents.getFemaleChildren() != 0) setFemaleChildren(dependents.getFemaleChildren());
        if (dependents.getOtherDependents() != 0) setOtherDependents(dependents.getOtherDependents());
    }

    public void delete() {
        deleted = true;
        deletedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Dependents{" +
                "id=" + id +
                ", spouse='" + spouse + '\'' +
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
