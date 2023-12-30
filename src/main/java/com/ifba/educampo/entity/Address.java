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
@Table(name = "addresses")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Address extends BaseEntity<Long> {
    private String street;

    @Column(nullable = false)
    private String city;

    private String number;

    private String complement;

    @Column(nullable = false)
    private String neighborhood;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    public void update(Address address) {
        if (address.getStreet() != null) setStreet(address.getStreet());
        if (address.getCity() != null) setCity(address.getCity());
        if (address.getNumber() != null) setNumber(address.getNumber());
        if (address.getComplement() != null) setComplement(address.getComplement());
        if (address.getNeighborhood() != null) setNeighborhood(address.getNeighborhood());
        if (address.getZipCode() != null) setZipCode(address.getZipCode());
    }

    @Override
    public String toString() {
        return "Address{" +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", number=" + number +
                ", complement='" + complement + '\'' +
                ", neighborhood='" + neighborhood + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
