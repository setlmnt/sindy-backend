package com.ifba.educampo.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ifba.educampo.model.enums.MaritalStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    @Column(name = "union_card", unique = true, nullable = false)
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
    @Column(name = "birth_at", nullable = false)
    private LocalDateTime birthAt; // Data de Nascimento

    @Column(name = "is_literate", nullable = false)
    private boolean isLiterate; // Alfabetizado

    @Column(name = "is_voter", nullable = false)
    private boolean isVoter; // Eleitor

    @Column(name = "marital_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus; // Estado Civil

    @Column(nullable = false)
    private Boolean deleted = false;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "association_at", nullable = false)
    private LocalDateTime associationAt; // Data de Associação

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // Data de Criação

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // Data de Atualização

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt; // Data de Atualização

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "local_office_id", nullable = true)
    private LocalOffice localOffice; // Delegacia (Escritório Local)

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", nullable = true)
    private Address address; // Endereço

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "dependents_id", nullable = false)
    private Dependents dependents; // Dependentes

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "affiliation_id", nullable = false)
    private Affiliation affiliation; // Filiação

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "place_of_birth_id", nullable = false)
    private PlaceOfBirth placeOfBirth; // Naturalidade

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "associate_photo_id")
    private AssociatePhoto associatePhoto; // Foto do Associado

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "work_record_id", nullable = false)
    private WorkRecord workRecord; // Carteira de Trabalho

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
        return "Associate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", unionCard=" + unionCard +
                ", cpf='" + cpf + '\'' +
                ", rg='" + rg + '\'' +
                ", profession='" + profession + '\'' +
                ", workplace='" + workplace + '\'' +
                ", phone='" + phone + '\'' +
                ", nationality='" + nationality + '\'' +
                ", birthAt=" + birthAt +
                ", isLiterate=" + isLiterate +
                ", isVoter=" + isVoter +
                ", maritalStatus=" + maritalStatus +
                ", deleted=" + deleted +
                ", associationAt=" + associationAt +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                '}';
    }
}
