package br.com.sindy.core.annotation;

import br.com.sindy.core.annotation.impl.FileTypeImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileTypeImpl.class)
public @interface FileType {
    String INVALID_FILE_TYPE = "Invalid file type";

    //error message
    String message() default INVALID_FILE_TYPE;

    //represents group of constraints
    Class<?>[] groups() default {};

    //represents additional information about annotation
    Class<? extends Payload>[] payload() default {};

    String[] type();
}
