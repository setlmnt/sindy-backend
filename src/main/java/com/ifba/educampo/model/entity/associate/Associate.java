package com.ifba.educampo.model.entity.associate;

import com.ifba.educampo.model.entity.Address;
import com.ifba.educampo.model.entity.Image;
import com.ifba.educampo.model.entity.LocalOffice;
import com.ifba.educampo.model.enums.MaritalStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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

    private String phone; // Telefone
    private String email; // E-mail

    @Column(nullable = false)
    private String nationality; // Nacionalidade

    @Temporal(TemporalType.DATE)
    @Column(name = "birth_at", nullable = false)
    private LocalDate birthAt; // Data de Nascimento

    @Column(name = "is_literate", nullable = false)
    private Boolean isLiterate; // Alfabetizado

    @Column(name = "is_voter", nullable = false)
    private Boolean isVoter; // Eleitor

    @Column(name = "marital_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus; // Estado Civil

    @Temporal(TemporalType.DATE)
    @Column(name = "association_at", nullable = false)
    private LocalDate associationAt; // Data de Associação

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "local_office_id")
    private LocalOffice localOffice; // Delegacia (Escritório Local)

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address; // Endereço

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dependents_id")
    private Dependents dependents; // Dependentes

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "affiliation_id", nullable = false)
    private Affiliation affiliation; // Filiação

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_of_birth_id", nullable = false)
    private PlaceOfBirth placeOfBirth; // Naturalidade

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image photo; // Foto do Associado

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_record_id", nullable = false)
    private WorkRecord workRecord; // Carteira de Trabalho

    @PrePersist
    private void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void update(Associate associate) {
        if (associate.getName() != null) setName(associate.getName());
        if (associate.getUnionCard() != null) setUnionCard(associate.getUnionCard());
        if (associate.getCpf() != null) setCpf(associate.getCpf());
        if (associate.getRg() != null) setRg(associate.getRg());
        if (associate.getProfession() != null) setProfession(associate.getProfession());
        if (associate.getWorkplace() != null) setWorkplace(associate.getWorkplace());
        if (associate.getPhone() != null) setPhone(associate.getPhone());
        if (associate.getNationality() != null) setNationality(associate.getNationality());
        if (associate.getBirthAt() != null) setBirthAt(associate.getBirthAt());
        if (associate.getIsLiterate() != null) setIsLiterate(associate.getIsLiterate());
        if (associate.getIsVoter() != null) setIsVoter(associate.getIsVoter());
        if (associate.getMaritalStatus() != null) setMaritalStatus(associate.getMaritalStatus());
        if (associate.getAssociationAt() != null) setAssociationAt(associate.getAssociationAt());
        if (associate.getLocalOffice() != null) setLocalOffice(associate.getLocalOffice());
        if (associate.getAddress() != null) setAddress(associate.getAddress());
        if (associate.getDependents() != null) setDependents(associate.getDependents());
        if (associate.getAffiliation() != null) setAffiliation(associate.getAffiliation());
        if (associate.getPlaceOfBirth() != null) setPlaceOfBirth(associate.getPlaceOfBirth());
        if (associate.getPhoto() != null) setPhoto(associate.getPhoto());
        if (associate.getWorkRecord() != null) setWorkRecord(associate.getWorkRecord());
    }

    public void delete() {
        deleted = true;
        deletedAt = LocalDateTime.now();
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
