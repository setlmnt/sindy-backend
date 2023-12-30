package com.ifba.educampo.entity;

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
@Table(name = "local_offices")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class LocalOffice extends BaseEntity<Long> {
    @Column(nullable = false)
    private String name;

    public void update(LocalOffice localOffice) {
        if (localOffice.getName() != null) setName(localOffice.getName());
    }

    @Override
    public String toString() {
        return "LocalOffice{" +
                ", name='" + name + '\'' +
                '}';
    }
}
