package com.bn.biteNest.common;

import java.io.File;

import jakarta.servlet.http.HttpSession;

public class FileDelete {
	public boolean deleteFile(String filePath, HttpSession session) {
	    // 실제 파일 경로를 가져옴
	    String realPath = session.getServletContext().getRealPath("/resources/uploadFiles/");
	    File fileToDelete = new File(realPath + filePath);

	    // 파일이 존재하는지 확인하고 삭제
	    if (fileToDelete.exists()) {
	        return fileToDelete.delete(); // 파일이 삭제되면 true 반환
	    } else {
	        System.out.println("파일이 존재하지 않습니다: " + filePath);
	        return false; // 파일이 없을 경우 false 반환
	    }
	}
}
