package br.com.sindy.api.controller;

import br.com.sindy.domain.dto.email.EmailResponseDto;
import br.com.sindy.domain.enums.EmailStatusEnum;
import br.com.sindy.domain.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Email", description = "Email API")
@RestController
@RequestMapping("/api/v1/emails")
@SecurityRequirements({
        @SecurityRequirement(name = "bearerAuth"),
        @SecurityRequirement(name = "cookieAuth")
})
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @Operation(summary = "List all emails with filters")
    @GetMapping
    public Page<EmailResponseDto> findAll(
            @RequestParam(required = false) String sender,
            @RequestParam(required = false) String recipient,
            @RequestParam(required = false) EmailStatusEnum status,
            Pageable pageable
    ) {
        return emailService.findAll(sender, recipient, status, pageable);
    }
}
