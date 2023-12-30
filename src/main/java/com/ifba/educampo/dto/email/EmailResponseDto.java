package com.ifba.educampo.dto.email;

import com.ifba.educampo.enums.EmailStatusEnum;

public record EmailResponseDto(
        Long id,
        String owner,
        String emailFrom,
        String emailTo,
        String subject,
        String text,
        EmailStatusEnum status
) {
}
