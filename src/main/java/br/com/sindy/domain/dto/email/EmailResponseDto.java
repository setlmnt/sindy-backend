package br.com.sindy.domain.dto.email;

import br.com.sindy.domain.entity.email.Recipient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class EmailResponseDto {
    private Long id;
    private String senderName;
    private String senderEmail;
    private List<RecipientResponseDto> recipients;
    private String message;
    private String subject;
    private String status;

    public EmailResponseDto(Long id, String senderName, String senderEmail, List<Recipient> recipients, String message, String subject, String status) {
        this.id = id;
        this.senderName = senderName;
        this.senderEmail = senderEmail;
        this.recipients = recipients.stream().map(r -> new RecipientResponseDto(r.getName(), r.getEmail())).toList();
        this.message = message;
        this.subject = subject;
        this.status = status;
    }
}
