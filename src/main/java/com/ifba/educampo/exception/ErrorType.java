package com.ifba.educampo.exception;

import lombok.Data;

@Data
public class ErrorType {
    private String message;
    private String field;

    public ErrorType(String message) {
        this.message = message;
    }

    public ErrorType(String message, String field) {
        this.message = message;
        this.field = field;
    }
}
