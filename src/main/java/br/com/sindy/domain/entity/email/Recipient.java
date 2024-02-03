package br.com.sindy.domain.entity.email;

import br.com.sindy.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.InheritanceType.SINGLE_TABLE;

@Getter
@Setter
@Entity
@Table(name = "recipients")
@Inheritance(strategy = SINGLE_TABLE)
public class Recipient extends BaseEntity<Long> {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "communication_history_id", nullable = false)
    private CommunicationHistory communicationHistory;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;
}
