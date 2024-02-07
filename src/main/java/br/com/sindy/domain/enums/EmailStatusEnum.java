package br.com.sindy.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmailStatusEnum {
    SENT("S", "sent"),
    ERROR("E", "error"),
    PENDING("P", "pending");

    private final String value;
    private final String description;
}
