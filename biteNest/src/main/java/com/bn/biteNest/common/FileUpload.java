package com.bn.biteNest.common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FileUpload {

    private final @Qualifier("fileUploadProperties")FileUploadProperties properties;


    public String saveImage(MultipartFile upfile, String recipeName, Integer stepNumber) {
        String originName = upfile.getOriginalFilename();  // ���� ���ϸ� ��������
        String ext = originName.substring(originName.lastIndexOf(".")); // ���� Ȯ���� ����
        int num = (int) (Math.random() * 900) + 100; // ���ϸ� ���� ���� ���� ���� (100 ~ 999 ������ ��)
        String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); // ���� �ð� ���� (yyyyMMddHHmmss)
        String formattedRecipeName = recipeName.replaceAll(" ", "_"); // ������ �̸����� ������ "_"�� ��ȯ

        // ���� ���Ͽ��� ������ ��� ���
        String savePath = properties.getUploadDir().endsWith("/") ? properties.getUploadDir() : properties.getUploadDir() + "/";  // ��� �������� "/"�� ������ �߰�

        // ���ϸ� ����
        String changeName;
        if (stepNumber == null) { 
            changeName = "BN_" + currentTime + "_" + formattedRecipeName + "_" + num + ext; // ���� �̹��� ���ϸ� ����
        } else {
            changeName = "BN_" + currentTime + "_" + formattedRecipeName + "_step" + stepNumber + "_" + num + ext; // ���� �̹��� ���ϸ� ����
        }

        try {
            // ��ΰ� �������� ������ ���丮 ����
            File directory = new File(savePath);
            if (!directory.exists()) {
                directory.mkdirs();  // ���� ���丮 ����
            }
            upfile.transferTo(new File(savePath + changeName)); // ������ �ش� ��ο� ����
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }

        return savePath + changeName;  // ����� ������ ��ü ��� ��ȯ
    }
}
