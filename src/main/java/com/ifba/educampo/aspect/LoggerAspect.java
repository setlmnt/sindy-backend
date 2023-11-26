package com.ifba.educampo.aspect;

import com.ifba.educampo.annotation.Log;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
public class LoggerAspect {
    @Pointcut(
            "@annotation(org.springframework.web.bind.annotation.GetMapping), " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping), " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping), " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping)"
    )
    public void requestMappingPointCut() {
    }

    @Around("@within(logAnnotation) && execution(* *(..))")
    public Object logMethodEntryAndExit(ProceedingJoinPoint joinPoint, Log logAnnotation) throws Throwable {
        Object result = null;

        try {
            Object[] args = joinPoint.getArgs();
            log.info("Entering method [{}] with args {}", joinPoint.getSignature(), args);

            long startTime = System.currentTimeMillis();
            result = joinPoint.proceed();
            long endTime = System.currentTimeMillis() - startTime;

            log.info("Exiting method [{}] with result: {}", joinPoint.getSignature(), result);
            log.info("Method [{}] executed in: {} ms", joinPoint.getSignature(), endTime);
        } catch (Exception e) {
            log.error("{} - {}", joinPoint.getSignature(), e.getMessage());
            throw e;
        }

        return result;
    }

    @Around("requestMappingPointCut()")
    public Object logRequestMapping(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

            log.info("Request {} {} - Parameters: {}", request.getMethod(), request.getRequestURI(), request.getParameterMap());

            result = joinPoint.proceed();

            if (result instanceof ResponseEntity<?> response) {
                log.info("Response: Status {} - Body: {}", response.getStatusCode(), response.getBody());
            }


        } catch (Exception e) {
            log.error("[{}] - {}", joinPoint.getSignature(), e.getMessage());
            throw e;
        }

        return result;
    }
}
