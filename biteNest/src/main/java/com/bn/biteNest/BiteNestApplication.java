package com.bn.biteNest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.bn.biteNest.common.FileUploadProperties;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
@EnableConfigurationProperties(FileUploadProperties.class)  // 이 부분이 있어야 외부 설정을 읽어올 수 있음
public class BiteNestApplication {
    public static void main(String[] args) {
        SpringApplication.run(BiteNestApplication.class, args);

    }
}