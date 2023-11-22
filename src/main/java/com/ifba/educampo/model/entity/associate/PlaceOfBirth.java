package com.ifba.educampo.model.entity.associate;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "places_of_birth")
public class PlaceOfBirth { // Local de Nascimento
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String city; // Cidade

    @Column(nullable = false)
    private String state; // Estado

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

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void update(PlaceOfBirth placeOfBirth) {
        if (placeOfBirth.getCity() != null) setCity(placeOfBirth.getCity());
        if (placeOfBirth.getState() != null) setState(placeOfBirth.getState());
    }

    public void delete() {
        deleted = true;
        deletedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "PlaceOfBirth{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", deleted=" + deleted +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                '}';
    }
}
