package com.bn.biteNest.common;

import java.io.File;  
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecipeFileUpload {

	private final FileUploadProperties properties;// 업로드 디렉토리 경로 설정 (application.yml 파일에서 읽어옴)
	public String saveImage(MultipartFile upfile, String recipeName, Integer stepNumber) {
        // 원본 파일명에서 확장자 추출
        String originName = upfile.getOriginalFilename();
        if (originName == null || originName.isEmpty()) {
            throw new IllegalArgumentException("업로드된 파일의 이름이 존재하지 않습니다.");
        }

        // 파일 확장자 및 기본 정보 생성
        String ext = originName.substring(originName.lastIndexOf("."));
        int randomNum = (int) (Math.random() * 900) + 100; // 100~999 사이의 랜덤 숫자 생성
        String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); // 현재 시간 포맷팅
        String formattedRecipeName = recipeName.replaceAll(" ", "_"); // 파일 이름에서 공백 제거

        // 절대 경로로 파일 저장 경로 설정
        String savePath = properties.getUploadDir().endsWith("/") ? properties.getUploadDir() : properties.getUploadDir() + "/";

        // 파일 이름 생성
        String changeName = generateFileName(currentTime, formattedRecipeName, stepNumber, randomNum, ext);

        // 디렉토리 생성 및 파일 저장
        File directory = new File(savePath);
        if (!directory.exists()) {
            boolean dirsCreated = directory.mkdirs(); // 디렉토리 생성
            if (!dirsCreated) {
                log.error("디렉토리 생성에 실패했습니다: {}", savePath);
                throw new RuntimeException("파일 저장 경로를 생성할 수 없습니다.");
            }
        }

        // 파일 저장
        try {
            File destinationFile = new File(savePath + changeName);
            upfile.transferTo(destinationFile);
            log.info("파일 저장 성공: {}", destinationFile.getAbsolutePath());
        } catch (IllegalStateException | IOException e) {
            log.error("파일 저장 중 오류 발생: {}", e.getMessage());
            throw new RuntimeException("파일 저장 중 오류가 발생했습니다.", e);
        }

        // 저장된 파일 경로 반환
        return savePath + changeName;
    }

    // 파일 이름 생성 메소드
    private String generateFileName(String currentTime, String formattedRecipeName, Integer stepNumber, int randomNum, String ext) {
        return String.format("BN_%s_%s%s_%d%s",currentTime,
			                formattedRecipeName,
			                (stepNumber != null) ? "_step" + stepNumber : "",
			                randomNum,
			                ext
        );
    }
}
