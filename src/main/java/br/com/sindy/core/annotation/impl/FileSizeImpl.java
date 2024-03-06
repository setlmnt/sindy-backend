package br.com.sindy.core.annotation.impl;

import br.com.sindy.core.annotation.FileSize;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

public class FileSizeImpl implements ConstraintValidator<FileSize, MultipartFile> {
    private DataSize maxSize;

    @Override
    public void initialize(FileSize constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.maxSize = DataSize.parse(constraintAnnotation.max());
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext ctx) {
        System.out.println(maxSize);
        return multipartFile == null || multipartFile.getSize() <= maxSize.toBytes();
    }
}
