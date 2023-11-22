package com.ifba.educampo.exception;

import lombok.Getter;

@Getter
public class InternalServerException extends RuntimeException {
    public InternalServerException(String message) {
        super(message);
    }
}
