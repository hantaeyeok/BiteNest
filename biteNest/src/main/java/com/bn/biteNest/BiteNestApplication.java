package com.bn.biteNest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.bn.biteNest.common.FileUploadProperties;

@EnableConfigurationProperties(FileUploadProperties.class)  
@SpringBootApplication(scanBasePackages = "com.bn")
public class BiteNestApplication {
    public static void main(String[] args) {
        SpringApplication.run(BiteNestApplication.class, args);
    }
}

