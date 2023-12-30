package com.ifba.educampo.entity.associate;

import com.ifba.educampo.entity.Address;
import com.ifba.educampo.entity.BaseEntity;
import com.ifba.educampo.entity.File;
import com.ifba.educampo.entity.LocalOffice;
import com.ifba.educampo.enums.MaritalStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "associates")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Associate extends BaseEntity<Long> {
    @Column(nullable = false)
    private String name;

    @Column(name = "union_card", unique = true, nullable = false)
    private Long unionCard;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(unique = true, nullable = false)
    private String rg;

    @Column(nullable = false)
    private String profession;

    @Column(nullable = false)
    private String workplace;

    private String phone;
    private String email;

    @Column(nullable = false)
    private String nationality;

    @Temporal(TemporalType.DATE)
    @Column(name = "birth_at", nullable = false)
    private LocalDate birthAt;

    @Column(name = "is_literate", nullable = false)
    private Boolean isLiterate;

    @Column(name = "is_voter", nullable = false)
    private Boolean isVoter;

    @Column(name = "marital_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private MaritalStatusEnum maritalStatusEnum;

    @Temporal(TemporalType.DATE)
    @Column(name = "association_at", nullable = false)
    private LocalDate associationAt;

    @Column(name = "is_paid", nullable = false)
    private Boolean isPaid = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "local_office_id")
    private LocalOffice localOffice;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dependents_id")
    private Dependents dependents;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "affiliation_id", nullable = false)
    private Affiliation affiliation;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_of_birth_id", nullable = false)
    private PlaceOfBirth placeOfBirth;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File profilePicture;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_record_id", nullable = false)
    private WorkRecord workRecord;

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
        if (associate.getMaritalStatusEnum() != null) setMaritalStatusEnum(associate.getMaritalStatusEnum());
        if (associate.getAssociationAt() != null) setAssociationAt(associate.getAssociationAt());
        if (associate.getLocalOffice() != null) setLocalOffice(associate.getLocalOffice());
        if (associate.getAddress() != null) setAddress(associate.getAddress());
        if (associate.getDependents() != null) setDependents(associate.getDependents());
        if (associate.getAffiliation() != null) setAffiliation(associate.getAffiliation());
        if (associate.getPlaceOfBirth() != null) setPlaceOfBirth(associate.getPlaceOfBirth());
        if (associate.getProfilePicture() != null) setProfilePicture(associate.getProfilePicture());
        if (associate.getWorkRecord() != null) setWorkRecord(associate.getWorkRecord());
    }

    @Override
    public String toString() {
        return "Associate{" +
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
                ", maritalStatus=" + maritalStatusEnum +
                ", associationAt=" + associationAt +
                '}';
    }
}
