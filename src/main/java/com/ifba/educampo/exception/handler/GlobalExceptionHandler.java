package com.ifba.educampo.exception.handler;

import com.ifba.educampo.exception.ErrorType;
import com.ifba.educampo.exception.InvalidAssociateException;
import com.ifba.educampo.exception.NotFoundException;
import com.ifba.educampo.exception.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        List<ErrorType> errorList = new ArrayList<>();
        fieldErrors.forEach(e -> {
            errorList.add(new ErrorType(e.getDefaultMessage(), e.getField()));
        });

        return new ExceptionResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                errorList,
                request.getDescription(false),
                new Date()
        );
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNotFoundException(NotFoundException ex, WebRequest request) {
        return new ExceptionResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                List.of(new ErrorType(ex.getMessage())),
                request.getDescription(false),
                new Date()
        );
    }

    @ExceptionHandler(InvalidAssociateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleInvalidAssociateException(InvalidAssociateException ex, WebRequest request) {
        return new ExceptionResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getErrors(),
                request.getDescription(false),
                new Date()
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
        return new ExceptionResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                List.of(new ErrorType(ex.getMessage())),
                request.getDescription(false),
                new Date()
        );
    }

   /* @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleException(Exception ex, WebRequest request) {
        return new ExceptionResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                List.of(new ErrorType(ex.getMessage())),
                request.getDescription(false),
                new Date()
        );
    }*/
}
