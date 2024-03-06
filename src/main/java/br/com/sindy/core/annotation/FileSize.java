package br.com.sindy.core.annotation;

import br.com.sindy.core.annotation.impl.FileSizeImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileSizeImpl.class)
public @interface FileSize {
    String FILE_SIZE_SHOULD_BE_LESS_THAN_MAX_BYTES = "File size should be less than {max} bytes";
    String MAX_SIZE = "1MB";

    //error message
    String message() default FILE_SIZE_SHOULD_BE_LESS_THAN_MAX_BYTES;

    //represents group of constraints
    Class<?>[] groups() default {};

    //represents additional information about annotation
    Class<? extends Payload>[] payload() default {};

    String max() default MAX_SIZE;
}
