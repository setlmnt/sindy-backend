package com.ifba.educampo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
@OpenAPIDefinition
public class EducampoApplication {
    private static final Logger LOGGER = Logger.getLogger(EducampoApplication.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Starting application...");
        SpringApplication.run(EducampoApplication.class, args);
        LOGGER.info("Application started.");
    }
}
