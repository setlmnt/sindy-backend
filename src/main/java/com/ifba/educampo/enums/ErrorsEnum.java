package com.ifba.educampo.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.awt.*;

@Getter
@RequiredArgsConstructor
public enum ErrorsEnum {
    FILE_MUST_BE_IMAGE("File must be an image", HttpStatus.BAD_REQUEST),
    FILE_MUST_BE_DOCUMENT("File must be a document", HttpStatus.BAD_REQUEST),
    ASSOCIATE_NOT_FOUND("Associate not found", HttpStatus.NOT_FOUND),
    INVALID_ASSOCIATE("Invalid associate", HttpStatus.BAD_REQUEST),
    DOCUMENT_NOT_FOUND("Document not found", HttpStatus.NOT_FOUND),
    INVALID_CREDENTIALS("Invalid credentials", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("Invalid token", HttpStatus.UNAUTHORIZED),
    ERROR_WHILE_LOADING_FILE("Error while loading file", HttpStatus.BAD_REQUEST),
    ERROR_WHILE_STORING_FILE("Error while storing file", HttpStatus.BAD_REQUEST),
    ERROR_WHILE_DELETE_FILE("Error while delete file", HttpStatus.BAD_REQUEST),
    ERROR_WHILE_CREATING_DIRECTORY("Error while creating directory", HttpStatus.BAD_REQUEST),
    LOCAL_OFFICE_NOT_FOUND("Local office not found", HttpStatus.NOT_FOUND),
    MONTHLY_FEE_NOT_FOUND("Monthly fee not found", HttpStatus.NOT_FOUND),
    INVALID_MONTHLY_FEE("Invalid monthly fee", HttpStatus.BAD_REQUEST),
    INVALID_OTP("Invalid OTP", HttpStatus.BAD_REQUEST),
    OTP_EXPIRED("OTP expired", HttpStatus.BAD_REQUEST),
    REPORT_NOT_FOUND("Report not found", HttpStatus.NOT_FOUND),
    ERROR_WHILE_GENERATING_REPORT("Error while generating report", HttpStatus.INTERNAL_SERVER_ERROR),
    SYNDICATE_NOT_FOUND("Syndicate not found", HttpStatus.NOT_FOUND),
    SYNDICATE_ALREADY_EXISTS("Syndicate already exists", HttpStatus.BAD_REQUEST),
    INVALID_USERNAME("Invalid username", HttpStatus.BAD_REQUEST),
    ASSOCIATE_IMAGE_NOT_FOUND("Associate image not found", HttpStatus.NOT_FOUND),
    ACCESS_DENIED("Access denied", HttpStatus.FORBIDDEN),
    INVALID_DATA("Invalid data", HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR("Internal error", HttpStatus.INTERNAL_SERVER_ERROR),
    RESOURCE_NOT_FOUND("Resource not found", HttpStatus.NOT_FOUND),
    INVALID_PARAMETER("Invalid parameter", HttpStatus.BAD_REQUEST),
    MESSAGE_NOT_READABLE("Message not readable", HttpStatus.BAD_REQUEST),
    EMAIL_TEMPLATE_NOT_FOUND("Email template not found", HttpStatus.NOT_FOUND),
    EMAIL_TEMPLATE_PROCESSING_ERROR("Error while processing email template", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String title;
    private final HttpStatus status;
}
