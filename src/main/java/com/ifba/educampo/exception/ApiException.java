package com.ifba.educampo.exception;

import com.ifba.educampo.enums.ErrorsEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ApiException extends RuntimeException {
    private final ErrorsEnum errorsEnum;
    private final String detail;
    private final List<ExceptionResponse.Field> fields;

    public ApiException(ErrorsEnum errorsEnum) {
        this.errorsEnum = errorsEnum;
        this.detail = null;
        this.fields = null;
    }

    public ApiException(ErrorsEnum errorsEnum, String detail) {
        this.errorsEnum = errorsEnum;
        this.detail = detail;
        this.fields = null;
    }

    public ApiException(ErrorsEnum errorsEnum, List<ExceptionResponse.Field> fields) {
        this.errorsEnum = errorsEnum;
        this.detail = null;
        this.fields = fields;
    }
}
