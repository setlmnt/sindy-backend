package com.ifba.educampo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MonthlyFeeException extends RuntimeException {
    public MonthlyFeeException(String message) {
        super(message);
    }
}
