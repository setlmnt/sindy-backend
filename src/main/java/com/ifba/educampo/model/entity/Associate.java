package com.ifba.educampo.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ifba.educampo.model.enums.MaritalStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "associates")
public class Associate { // Associado
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Nome

    @Column(unique = true, nullable = false)
    private Long unionCard; // Carteira Sindical

    @Column(unique = true, nullable = false)
    private String cpf; // CPF

    @Column(unique = true, nullable = false)
    private String rg; // RG

    @Column(nullable = false)
    private String profession; // Profissão

    @Column(nullable = false)
    private String workplace; // Local de Trabalho

    @Column(nullable = false)
    private String phone; // Telefone

    @Column(nullable = false)
    private String nationality; // Nacionalidade

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private java.util.Date birthDate; // Data de Nascimento

    @Column(nullable = false)
    private boolean isLiterate; // Alfabetizado

    @Column(nullable = false)
    private boolean isVoter; // Eleitor

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus; // Estado Civil

    @Column(nullable = false)
    private java.util.Date associationDate; // Data de Associação

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdAt", nullable = false, updatable = false)
    private java.util.Date createdAt; // Data de Criação

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatedAt", nullable = false)
    private java.util.Date updatedAt; // Data de Atualização

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "localOfficeId", nullable = true)
    private LocalOffice localOffice; // Delegacia (Escritório Local)

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "addressId", nullable = true)
    private Address address; // Endereço

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "dependentsId", nullable = false)
    private Dependents dependents; // Dependentes

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "affiliationId", nullable = false)
    private Affiliation affiliation; // Filiação

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "placeOfBirthId", nullable = false)
    private PlaceOfBirth placeOfBirth; // Naturalidade

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "associatePhotoId", nullable = false)
    private AssociatePhoto associatePhoto; // Foto do Associado

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "workRecordId", nullable = false)
    private WorkRecord workRecord; // Carteira de Trabalho

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
