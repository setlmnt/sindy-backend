package com.ifba.educampo.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ifba.educampo.annotation.Log;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
@Slf4j
@Log
public class GlobalExceptionHandler {
    private static List<ErrorType> getErrors(List<FieldError> fieldErrors) {
        return fieldErrors
                .stream()
                .map(fieldError -> new ErrorType(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleBadRequestException(BadRequestException ex) {
        log.error("Bad request", ex);
        return new ExceptionResponse(
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                HttpStatus.BAD_REQUEST.value(),
                List.of(new ErrorType("body", ex.getMessage())),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handleForbiddenException(ForbiddenException ex) {
        log.error("Bad request", ex);
        return new ExceptionResponse(
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                HttpStatus.FORBIDDEN.value(),
                List.of(new ErrorType("body", ex.getMessage())),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(BadRequestListException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleBadRequestListException(BadRequestListException ex) {
        log.error("Bad request list", ex);
        return new ExceptionResponse(
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getErrors(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNotFoundException(NotFoundException ex) {
        log.error("Not Found", ex);
        return new ExceptionResponse(
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                HttpStatus.NOT_FOUND.value(),
                List.of(new ErrorType("body", ex.getMessage())),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Method argument not valid", ex);
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ErrorType> errors = getErrors(fieldErrors);

        return new ExceptionResponse(
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                HttpStatus.BAD_REQUEST.value(),
                errors,
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("Http message not readable", ex);
        return new ExceptionResponse(
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                HttpStatus.BAD_REQUEST.value(),
                List.of(new ErrorType("body", ex.getLocalizedMessage())),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleException(Exception ex) {
        log.error("Internal server error", ex);
        return new ExceptionResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                List.of(new ErrorType("body", ex.getLocalizedMessage())),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleEntityNotFoundException(EntityNotFoundException ex) {
        log.error("Entity not found", ex);
        return new ExceptionResponse(
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                HttpStatus.NOT_FOUND.value(),
                List.of(new ErrorType("body", ex.getMessage())),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionResponse handleForbiddenException(UnauthorizedException ex) {
        log.error("Unauthorized", ex);
        return new ExceptionResponse(
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                HttpStatus.UNAUTHORIZED.value(),
                List.of(new ErrorType("body", ex.getMessage())),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handleAccessDeniedException(AccessDeniedException ex) {
        log.error("Access Denied", ex);
        return new ExceptionResponse(
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                HttpStatus.FORBIDDEN.value(),
                List.of(new ErrorType("body", ex.getMessage())),
                LocalDateTime.now()
        );
    }

    public record ExceptionResponse(
            String message,
            Integer status,
            List<ErrorType> errors,
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            LocalDateTime timestamp
    ) {
    }
}
