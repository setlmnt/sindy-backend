package com.ifba.educampo.domain.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse {
    private String title;
    private Integer status;
    private String detail;
    private String path;
    private List<Field> fields;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    @Getter
    @Builder
    public static class Field {
        private String message;
        private String field;

        public Field(String message) {
            this.message = message;
        }

        public Field(String message, String field) {
            this.message = message;
            this.field = field;
        }
    }
}
