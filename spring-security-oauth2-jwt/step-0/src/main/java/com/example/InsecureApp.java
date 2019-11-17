package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InsecureApp {
    public static void main(String[] args) {
        SpringApplication.run(InsecureApp.class, args);
    }
}
