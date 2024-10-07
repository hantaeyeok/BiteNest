package com.bn.biteNest.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Primary
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "file")
public class FileUploadProperties {
    private String uploadDir;
}
