package com.ifba.educampo.exception.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ifba.educampo.exception.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@JsonSerialize(using = ExceptionResponseSerializer.class)
public class ExceptionResponse {
    private int status;
    private String message;
    private List<ErrorType> errors;
    private String details;
    private Date timestamp;
}

