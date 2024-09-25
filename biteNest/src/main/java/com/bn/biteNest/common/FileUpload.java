package com.bn.biteNest.common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

public class FileUpload {
	public String saveImage(MultipartFile upfile, HttpSession session, String recipeName, Integer stepNumber) {

	    String originName = upfile.getOriginalFilename();  // 원본 파일명 가져오기
	    String ext = originName.substring(originName.lastIndexOf("."));// 파일 확장자 추출
	    int num = (int) (Math.random() * 900) + 100;// 파일명에 붙일 랜덤 숫자 생성 (100 ~ 999 사이의 값)
	    String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());// 현재 시간 형식 (yyyyMMddHHmmss)
	    String formattedRecipeName = recipeName.replaceAll(" ", "_");// 레시피 이름에서 공백을 "_"로 변환

	    String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/");// 파일 저장 경로 설정

	    String changeName;// 파일명 생성 (스탭 이미지인 경우 스탭 번호를 포함)
	    if (stepNumber == null) { 
	        changeName = "BN_" + currentTime + "_" + formattedRecipeName + "_" + num + ext;// 메인 이미지 파일명 형식: BN_currentTime_레시피이름_num_ext
	    } else {
	        changeName = "BN_" + currentTime + "_" + formattedRecipeName + "_step" + stepNumber + "_" + num + ext;// 스탭 이미지 파일명 형식: BN_currentTime_레시피이름_스탭번호_num_ext
	    }

	    try {
	        upfile.transferTo(new File(savePath + changeName)); // 파일을 해당 경로에 저장
	    } catch (IllegalStateException | IOException e) {
	        e.printStackTrace();
	    }

	    return savePath + changeName;  // 저장된 파일의 전체 경로 반환
	}
}
