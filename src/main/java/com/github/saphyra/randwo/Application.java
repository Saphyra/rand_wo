package com.github.saphyra.randwo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.saphyra.exceptionhandling.EnableExceptionHandler;

@SpringBootApplication
@EnableExceptionHandler
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
