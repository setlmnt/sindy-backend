package com.ifba.educampo.entity;

import com.ifba.educampo.enums.StatusEmailEnum;
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
@Table(name = "emails")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Email extends BaseEntity<Long> {
    @Column(nullable = false)
    private String owner;

    @Column(name = "email_from", nullable = false)
    private String emailFrom;

    @Column(name = "email_to", nullable = false)
    private String emailTo;

    @Column(nullable = false)
    private String subject;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String text;

    @Column(nullable = false)
    private StatusEmailEnum status = StatusEmailEnum.PENDING;

    @Override
    public String toString() {
        return "Email{" +
                "owner='" + owner + '\'' +
                ", emailFrom='" + emailFrom + '\'' +
                ", emailTo='" + emailTo + '\'' +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                ", status=" + status +
                '}';
    }
}
