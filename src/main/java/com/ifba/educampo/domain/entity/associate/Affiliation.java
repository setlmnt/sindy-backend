package com.ifba.educampo.domain.entity.associate;


import com.ifba.educampo.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "affiliations")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Affiliation extends BaseEntity<Long> {
    @Column(name = "father_name", nullable = false)
    private String fatherName;

    @Column(name = "mother_name", nullable = false)
    private String motherName;

    public void update(Affiliation affiliation) {
        if (affiliation.getFatherName() != null) setFatherName(affiliation.getFatherName());
        if (affiliation.getMotherName() != null) setMotherName(affiliation.getMotherName());
    }

    @Override
    public String toString() {
        return "Affiliation{" +
                ", fatherName='" + fatherName + '\'' +
                ", motherName='" + motherName + '\'' +
                '}';
    }
}
