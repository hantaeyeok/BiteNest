package com.bn.biteNest.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
@ConfigurationProperties(prefix = "file")  // yml�� file �Ӽ��� �о���� ����
public class FileUploadProperties {
    private String uploadDir;  // file.upload-dir�� ������ ����

    // Getter�� Setter
    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
