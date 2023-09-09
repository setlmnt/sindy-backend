package com.ifba.educampo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MonthlyFeeExcepetion extends RuntimeException {
    public MonthlyFeeExcepetion(String message) {
        super(message);
    }
}