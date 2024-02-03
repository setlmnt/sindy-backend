package br.com.sindy.domain.dto.email;

import br.com.sindy.domain.entity.email.Recipient;
import br.com.sindy.domain.enums.EmailStatusEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class EmailResponseDto {
    private Long id;
    private String sender;
    private List<RecipientResponseDto> recipients;
    private String message;
    private String subject;
    private EmailStatusEnum status;

    public EmailResponseDto(Long id, String sender, List<Recipient> recipients, String message, String subject, EmailStatusEnum status) {
        this.id = id;
        this.sender = sender;
        this.recipients = recipients.stream().map(r -> new RecipientResponseDto(r.getName(), r.getEmail())).toList();
        this.message = message;
        this.subject = subject;
        this.status = status;
    }
}
