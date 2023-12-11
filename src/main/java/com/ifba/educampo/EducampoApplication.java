package com.ifba.educampo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
@Slf4j
public class EducampoApplication {
    public static void main(String[] args) {
        log.info("Starting application...");
        SpringApplication.run(EducampoApplication.class, args);
        log.info("Application started.");
    }
}
