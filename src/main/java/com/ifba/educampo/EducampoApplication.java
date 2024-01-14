package com.ifba.educampo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@Slf4j
@OpenAPIDefinition
@SpringBootApplication
@EnableAsync
@EnableCaching
public class EducampoApplication {
    public static void main(String[] args) {
        SpringApplication.run(EducampoApplication.class, args);
    }
}
