package com.example.project1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Project1Application implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(Project1Application.class, args);
    }

}
