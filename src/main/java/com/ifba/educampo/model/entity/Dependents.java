package com.ifba.educampo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dependents")
public class Dependents { // Dependentes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String wifeName; // Nome da Esposa

    @Column(nullable = false)
    private int minorChildren; // Filhos menores

    @Column(nullable = false)
    private int maleChildren; // Filhos homens

    @Column(nullable = false)
    private int femaleChildren; // Filhas mulheres

    @Column(nullable = false)
    private int otherDependents; // Outros dependentes

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdAt", nullable = false, updatable = false)
    private java.util.Date createdAt; // Data de criação

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatedAt", nullable = false)
    private java.util.Date updatedAt; // Data de atualização

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
