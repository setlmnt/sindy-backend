package com.ifba.educampo.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmailStatusEnum {
    SENT("S"),
    ERROR("E"),
    PENDING("P");

    private final String value;
}
