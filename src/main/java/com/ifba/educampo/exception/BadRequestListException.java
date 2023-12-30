package com.ifba.educampo.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class BadRequestListException extends RuntimeException {

    private final List<ErrorType> errors;

    public BadRequestListException(String message, List<ErrorType> errorList) {
        super(message);
        this.errors = errorList;
    }
}