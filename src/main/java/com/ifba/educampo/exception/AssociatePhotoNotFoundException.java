package com.ifba.educampo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AssociatePhotoNotFoundException extends RuntimeException {

    public AssociatePhotoNotFoundException(String message) {
        super(message);
    }

}
