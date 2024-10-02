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
        String originName = upfile.getOriginalFilename();  // 원본 파일명 가져오기
        String ext = originName.substring(originName.lastIndexOf(".")); // 파일 확장자 추출
        int num = (int) (Math.random() * 900) + 100; // 파일명에 붙일 랜덤 숫자 생성 (100 ~ 999 사이의 값)
        String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); // 현재 시간 형식 (yyyyMMddHHmmss)
        String formattedRecipeName = recipeName.replaceAll(" ", "_"); // 레시피 이름에서 공백을 "_"로 변환

        // 설정 파일에서 가져온 경로 사용
        String savePath = properties.getUploadDir().endsWith("/") ? properties.getUploadDir() : properties.getUploadDir() + "/";  // 경로 마지막에 "/"가 없으면 추가

        // 파일명 생성
        String changeName;
        if (stepNumber == null) { 
            changeName = "BN_" + currentTime + "_" + formattedRecipeName + "_" + num + ext; // 메인 이미지 파일명 형식
        } else {
            changeName = "BN_" + currentTime + "_" + formattedRecipeName + "_step" + stepNumber + "_" + num + ext; // 스탭 이미지 파일명 형식
        }

        try {
            // 경로가 존재하지 않으면 디렉토리 생성
            File directory = new File(savePath);
            if (!directory.exists()) {
                directory.mkdirs();  // 저장 디렉토리 생성
            }
            upfile.transferTo(new File(savePath + changeName)); // 파일을 해당 경로에 저장
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }

        return savePath + changeName;  // 저장된 파일의 전체 경로 반환
    }
}
