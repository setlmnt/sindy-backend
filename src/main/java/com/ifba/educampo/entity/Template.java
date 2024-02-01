package com.ifba.educampo.entity;

import com.ifba.educampo.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "templates")
public class Template extends BaseEntity<Long> {
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "body", columnDefinition = "TEXT", nullable = false)
    private String body;
}
