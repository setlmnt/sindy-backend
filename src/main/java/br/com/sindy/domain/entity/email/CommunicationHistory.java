package br.com.sindy.domain.entity.email;

import br.com.sindy.domain.entity.BaseEntity;
import br.com.sindy.domain.entity.Template;
import br.com.sindy.domain.enums.EmailStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.InheritanceType.SINGLE_TABLE;

@Getter
@Setter
@Entity
@Table(name = "communication_histories")
@Inheritance(strategy = SINGLE_TABLE)
public class CommunicationHistory extends BaseEntity<Long> {
    @Column(name = "sender_name", nullable = false)
    private String senderName;

    @Column(name = "sender_email", nullable = false)
    private String senderEmail;

    @Column(name = "subject")
    private String subject;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "status", nullable = false)
    private EmailStatusEnum status = EmailStatusEnum.PENDING;

    @ManyToMany
    @JoinTable(
            name = "communication_history_templates",
            joinColumns = @JoinColumn(name = "communication_history_id"),
            inverseJoinColumns = @JoinColumn(name = "template_id")
    )
    private List<Template> templates;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "communicationHistory")
    private List<Recipient> recipients;
}
