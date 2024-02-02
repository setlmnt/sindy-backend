package com.ifba.educampo.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.ifba.educampo.enums.ErrorsEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String DEFAULT_MESSAGE = "An unexpected internal system error has occurred. " +
            "Try again and if the problem persists, contact your system administrator.";

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ExceptionResponse> handleApiException(ApiException ex, HttpServletRequest request) {
        log.error("API exception in {}", request.getRequestURI(), ex);
        ExceptionResponse exceptionResponse = getExceptionResponse(
                ex.getErrorsEnum().getTitle(),
                ex.getErrorsEnum().getStatus().value()
        )
                .detail(ex.getDetail())
                .fields(ex.getFields())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(exceptionResponse, ex.getErrorsEnum().getStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        log.error("Access Denied", ex);
        return getExceptionResponse(
                ErrorsEnum.ACCESS_DENIED.getTitle(),
                ErrorsEnum.ACCESS_DENIED.getStatus().value()
        )
                .path(request.getRequestURI())
                .build();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        log.error("Method argument not valid", ex);

        ErrorsEnum errorType = ErrorsEnum.INVALID_DATA;
        String detail = "One or more fields are invalid. Fill in correctly and try again.";
        BindingResult bindingResult = ex.getBindingResult();

        List<ExceptionResponse.Field> errors = bindingResult.getAllErrors()
                .stream()
                .map(error -> {
                    String name = error.getObjectName();

                    if (error instanceof FieldError) {
                        name = ((FieldError) error).getField();
                    }

                    return ExceptionResponse.Field.builder().field(name).message(error.getDefaultMessage()).build();
                })
                .toList();

        ExceptionResponse exceptionResponse = getExceptionResponse(errorType.getTitle(), status.value())
                .detail(detail)
                .fields(errors)
                .path(getInstance(request))
                .build();

        return handleExceptionInternal(ex, exceptionResponse, headers, status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        log.error("Uncaught exception", ex);

        String detail = DEFAULT_MESSAGE;

        ExceptionResponse exceptionResponse = getExceptionResponse(
                ErrorsEnum.INTERNAL_ERROR.getTitle(),
                ErrorsEnum.INTERNAL_ERROR.getStatus().value()
        )
                .detail(detail)
                .path(getInstance(request))
                .build();

        return handleExceptionInternal(ex, exceptionResponse, new HttpHeaders(), ErrorsEnum.INTERNAL_ERROR.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        log.error("No handler found", ex);

        String detail = String.format("Resource %s not found", ex.getRequestURL());

        ExceptionResponse exceptionResponse = getExceptionResponse(
                ErrorsEnum.INTERNAL_ERROR.getTitle(),
                status.value()
        )
                .detail(detail)
                .path(getInstance(request))
                .build();

        return handleExceptionInternal(ex, exceptionResponse, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        log.error("Type mismatch", ex);

        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch(
                    (MethodArgumentTypeMismatchException) ex,
                    headers,
                    status,
                    request
            );
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        log.error("Method argument type mismatch", ex);

        String detail = String.format(
                "The URL parameter '%s' received the value '%s', which is of an invalid type. Correct and enter a value compatible with the type %s.",
                ex.getName(), ex.getValue(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName()
        );

        ExceptionResponse exceptionResponse = getExceptionResponse(
                ErrorsEnum.INVALID_PARAMETER.getTitle(),
                status.value()
        )
                .detail(detail)
                .path(getInstance(request))
                .build();

        return handleExceptionInternal(ex, exceptionResponse, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        log.error("Http message not readable", ex);

        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
        }

        String detail = "The request body is invalid. Check syntax error.";

        ExceptionResponse exceptionResponse = getExceptionResponse(
                ErrorsEnum.MESSAGE_NOT_READABLE.getTitle(),
                status.value()
        )
                .detail(detail)
                .path(getInstance(request))
                .build();

        return handleExceptionInternal(ex, exceptionResponse, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBinding(
            PropertyBindingException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        log.error("Property binding", ex);

        String path = joinPath(ex.getPath());

        String detail = String.format("The property '%s' does not exist. Correct or remove this property and try again.", path);

        ExceptionResponse exceptionResponse = getExceptionResponse(
                ErrorsEnum.MESSAGE_NOT_READABLE.getTitle(),
                status.value()
        )
                .detail(detail)
                .path(getInstance(request))
                .build();

        return handleExceptionInternal(ex, exceptionResponse, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormat(
            InvalidFormatException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        log.error("Invalid format", ex);

        String path = joinPath(ex.getPath());

        String detail = String.format(
                "The property '%s' received the value '%s', which is of an invalid type. Correct and enter a value compatible with the type %s.",
                path, ex.getValue(), ex.getTargetType().getSimpleName()
        );

        ExceptionResponse exceptionResponse = getExceptionResponse(
                ErrorsEnum.MESSAGE_NOT_READABLE.getTitle(),
                status.value()
        )
                .detail(detail)
                .path(getInstance(request))
                .build();

        return handleExceptionInternal(ex, exceptionResponse, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        log.error("Exception internal", ex);

        if (body == null) {
            body = ExceptionResponse.builder()
                    .timestamp(LocalDateTime.now())
                    .title(status.toString())
                    .status(status.value())
                    .detail(DEFAULT_MESSAGE)
                    .path(getInstance(request))
                    .build();
        } else if (body instanceof String) {
            body = ExceptionResponse.builder()
                    .timestamp(LocalDateTime.now())
                    .title((String) body)
                    .status(status.value())
                    .detail(DEFAULT_MESSAGE)
                    .path(getInstance(request))
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private String getInstance(WebRequest request) {
        return request.getContextPath() + request.getDescription(false).substring(4);
    }

    private ExceptionResponse.ExceptionResponseBuilder getExceptionResponse(String title, int status) {
        return ExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status)
                .title(title);
    }

    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
    }
}
