package br.com.sindy.core.annotation.impl;

import br.com.sindy.core.annotation.FileType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class FileTypeImpl implements ConstraintValidator<FileType, MultipartFile> {
    private List<String> types;

    @Override
    public void initialize(FileType constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.types = Arrays.asList(constraintAnnotation.type());
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext ctx) {
        return multipartFile == null || types.contains(multipartFile.getContentType());
    }
}
