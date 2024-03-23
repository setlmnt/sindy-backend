package br.com.sindy;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.TimeZone;

@Slf4j
@OpenAPIDefinition
@SpringBootApplication
@EnableAsync
public class SindyApplication {
    public static final String TIME_ZONE = "UTC";

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone(TIME_ZONE));
        SpringApplication.run(SindyApplication.class, args);
    }
}
