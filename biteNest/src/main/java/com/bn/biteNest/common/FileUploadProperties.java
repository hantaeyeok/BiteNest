package com.bn.biteNest.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
@ConfigurationProperties(prefix = "file")  // yml의 file 속성을 읽어오는 설정
public class FileUploadProperties {
    private String uploadDir;  // file.upload-dir을 저장할 변수

    // Getter와 Setter
    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
