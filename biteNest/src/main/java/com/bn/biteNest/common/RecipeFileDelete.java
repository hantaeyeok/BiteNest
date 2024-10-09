package com.bn.biteNest.common;

import java.io.File;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecipeFileDelete {

    private final FileUploadProperties properties;
    public boolean deleteFile(String filePath) {
        // 절대 경로로 파일 저장 경로 설정
        String fullFilePath = properties.getUploadDir() + filePath;

        // 삭제할 파일 객체 생성
        File fileToDelete = new File(fullFilePath);

        // 파일이 존재하는지 확인하고 삭제
        if (fileToDelete.exists()) {
            boolean isDeleted = fileToDelete.delete(); // 파일 삭제 시도
            if (isDeleted) {
                log.info("파일 삭제 성공: {}", fullFilePath);
            } else {
                log.error("파일 삭제 실패: {}", fullFilePath);
            }
            return isDeleted;
        } else {
            log.warn("파일이 존재하지 않습니다: {}", fullFilePath);
            return false; // 파일이 존재하지 않는 경우
        }
    }
}