package com.ifba.educampo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class EducampoApplication {
    public static void main(String[] args) {
        SpringApplication.run(EducampoApplication.class, args);
    }
}
