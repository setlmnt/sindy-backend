package com.ifba.educampo.exception.response;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.ifba.educampo.exception.ErrorType;

import java.io.IOException;

public class ExceptionResponseSerializer extends JsonSerializer<ExceptionResponse> {

    @Override
    public void serialize(ExceptionResponse exceptionResponse, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField("status", String.valueOf(exceptionResponse.getStatus()));
        jsonGenerator.writeStringField("message", exceptionResponse.getMessage());
        jsonGenerator.writeStringField("details", exceptionResponse.getDetails());
        jsonGenerator.writeStringField("timestamp", exceptionResponse.getTimestamp().toString());

        if (exceptionResponse.getErrors() != null) {
            jsonGenerator.writeFieldName("errors");
            jsonGenerator.writeStartArray();
            for (ErrorType errorType : exceptionResponse.getErrors()) {
                jsonGenerator.writeStartObject();
                if (errorType.getField() != null) {
                    jsonGenerator.writeStringField("field", errorType.getField());
                }
                jsonGenerator.writeStringField("message", errorType.getMessage());
                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();
        }

        jsonGenerator.writeEndObject();
    }
}
