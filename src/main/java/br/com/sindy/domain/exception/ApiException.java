package br.com.sindy.domain.exception;

import br.com.sindy.domain.enums.ErrorEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ApiException extends RuntimeException {
    private final ErrorEnum errorEnum;
    private final String detail;
    private final List<ExceptionResponse.Field> fields;

    public ApiException(ErrorEnum errorEnum) {
        this.errorEnum = errorEnum;
        this.detail = null;
        this.fields = null;
    }

    public ApiException(ErrorEnum errorEnum, String detail) {
        this.errorEnum = errorEnum;
        this.detail = detail;
        this.fields = null;
    }

    public ApiException(ErrorEnum errorEnum, List<ExceptionResponse.Field> fields) {
        this.errorEnum = errorEnum;
        this.detail = null;
        this.fields = fields;
    }
}
