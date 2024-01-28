package com.ifba.educampo.exception;

import com.ifba.educampo.enums.ErrorsEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiException extends RuntimeException {
    private final ErrorsEnum errorsEnum;
}
