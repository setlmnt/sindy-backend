package com.ifba.educampo.entity.user;

import com.ifba.educampo.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "otps")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Otp extends BaseEntity<Long> {
    @Column(nullable = false)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public boolean isExpired() {
        return this.getCreatedAt().plusMinutes(30).isBefore(LocalDateTime.now());
    }
}
