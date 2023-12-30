package com.ifba.educampo.entity.associate;

import com.ifba.educampo.entity.BaseEntity;
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
@Table(name = "dependents")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Dependents extends BaseEntity<Long> {
    private String spouse;

    @Column(name = "minor_children")
    private int minorChildren;

    @Column(name = "male_children")
    private int maleChildren;

    @Column(name = "female_children")
    private int femaleChildren;

    @Column(name = "other_dependents")
    private int otherDependents;

    public void update(Dependents dependents) {
        if (dependents.getSpouse() != null) setSpouse(dependents.getSpouse());
        if (dependents.getMinorChildren() != 0) setMinorChildren(dependents.getMinorChildren());
        if (dependents.getMaleChildren() != 0) setMaleChildren(dependents.getMaleChildren());
        if (dependents.getFemaleChildren() != 0) setFemaleChildren(dependents.getFemaleChildren());
        if (dependents.getOtherDependents() != 0) setOtherDependents(dependents.getOtherDependents());
    }

    @Override
    public String toString() {
        return "Dependents{" +
                ", spouse='" + spouse + '\'' +
                ", minorChildren=" + minorChildren +
                ", maleChildren=" + maleChildren +
                ", femaleChildren=" + femaleChildren +
                ", otherDependents=" + otherDependents +
                '}';
    }
}
