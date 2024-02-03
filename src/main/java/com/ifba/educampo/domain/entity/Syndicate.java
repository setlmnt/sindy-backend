package com.ifba.educampo.domain.entity;

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
@Table(name = "syndicate")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Syndicate extends BaseEntity<Long> {
    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String cnpj;

    @Temporal(TemporalType.DATE)
    private LocalDate foundationDate;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    public void update(Syndicate syndicate) {
        if (syndicate.getName() != null) {
            this.setName(syndicate.getName());
        }

        if (syndicate.getCnpj() != null) {
            this.setCnpj(syndicate.getCnpj());
        }

        if (syndicate.getFoundationDate() != null) {
            this.setFoundationDate(syndicate.getFoundationDate());
        }
    }
}
