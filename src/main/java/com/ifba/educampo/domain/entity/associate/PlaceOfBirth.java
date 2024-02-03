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
@Table(name = "places_of_birth")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class PlaceOfBirth extends BaseEntity<Long> {
    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    public void update(PlaceOfBirth placeOfBirth) {
        if (placeOfBirth.getCity() != null) setCity(placeOfBirth.getCity());
        if (placeOfBirth.getState() != null) setState(placeOfBirth.getState());
    }

    @Override
    public String toString() {
        return "PlaceOfBirth{" +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
