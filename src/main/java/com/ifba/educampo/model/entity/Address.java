package com.ifba.educampo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "addresses")
public class Address { // Endereço
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String street; // Rua

    @Column(nullable = false)
    private String city; // Cidade

    @Column(nullable = false)
    private int number; // Número

    @Column(nullable = false)
    private String complement; // Complemento

    @Column(nullable = false)
    private String neighborhood; // Bairro

    @Column(nullable = false)
    private String zipCode; // CEP

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
