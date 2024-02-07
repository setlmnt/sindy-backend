package br.com.sindy.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorEnum {
    FILE_MUST_BE_IMAGE("File must be an image", ErrorTypeEnum.BAD_REQUEST),
    FILE_MUST_BE_DOCUMENT("File must be a document", ErrorTypeEnum.BAD_REQUEST),
    ASSOCIATE_NOT_FOUND("Associate not found", ErrorTypeEnum.NOT_FOUND),
    INVALID_ASSOCIATE("Invalid associate", ErrorTypeEnum.BAD_REQUEST),
    DOCUMENT_NOT_FOUND("Document not found", ErrorTypeEnum.NOT_FOUND),
    INVALID_CREDENTIALS("Invalid credentials", ErrorTypeEnum.UNAUTHORIZED),
    INVALID_TOKEN("Invalid token", ErrorTypeEnum.UNAUTHORIZED),
    ERROR_WHILE_LOADING_FILE("Error while loading file", ErrorTypeEnum.BAD_REQUEST),
    ERROR_WHILE_STORING_FILE("Error while storing file", ErrorTypeEnum.BAD_REQUEST),
    ERROR_WHILE_DELETE_FILE("Error while delete file", ErrorTypeEnum.BAD_REQUEST),
    ERROR_WHILE_CREATING_DIRECTORY("Error while creating directory", ErrorTypeEnum.BAD_REQUEST),
    LOCAL_OFFICE_NOT_FOUND("Local office not found", ErrorTypeEnum.NOT_FOUND),
    MONTHLY_FEE_NOT_FOUND("Monthly fee not found", ErrorTypeEnum.NOT_FOUND),
    INVALID_MONTHLY_FEE("Invalid monthly fee", ErrorTypeEnum.BAD_REQUEST),
    INVALID_OTP("Invalid OTP", ErrorTypeEnum.BAD_REQUEST),
    OTP_EXPIRED("OTP expired", ErrorTypeEnum.BAD_REQUEST),
    REPORT_NOT_FOUND("Report not found", ErrorTypeEnum.NOT_FOUND),
    ERROR_WHILE_GENERATING_REPORT("Error while generating report", ErrorTypeEnum.INTERNAL_ERROR),
    SYNDICATE_NOT_FOUND("Syndicate not found", ErrorTypeEnum.NOT_FOUND),
    SYNDICATE_ALREADY_EXISTS("Syndicate already exists", ErrorTypeEnum.BAD_REQUEST),
    INVALID_USERNAME("Invalid username", ErrorTypeEnum.BAD_REQUEST),
    ASSOCIATE_IMAGE_NOT_FOUND("Associate image not found", ErrorTypeEnum.NOT_FOUND),
    ACCESS_DENIED("Access denied", ErrorTypeEnum.FORBIDDEN),
    INVALID_DATA("Invalid data", ErrorTypeEnum.BAD_REQUEST),
    INTERNAL_ERROR("Internal error", ErrorTypeEnum.INTERNAL_ERROR),
    RESOURCE_NOT_FOUND("Resource not found", ErrorTypeEnum.NOT_FOUND),
    INVALID_PARAMETER("Invalid parameter", ErrorTypeEnum.BAD_REQUEST),
    MESSAGE_NOT_READABLE("Message not readable", ErrorTypeEnum.BAD_REQUEST),
    EMAIL_TEMPLATE_NOT_FOUND("Email template not found", ErrorTypeEnum.NOT_FOUND),
    EMAIL_TEMPLATE_PROCESSING_ERROR("Error while processing email template", ErrorTypeEnum.INTERNAL_ERROR);

    private final String title;
    private final ErrorTypeEnum status;
}
