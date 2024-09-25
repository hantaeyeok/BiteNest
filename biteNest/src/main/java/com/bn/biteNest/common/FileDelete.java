package com.bn.biteNest.common;

import java.io.File;

import jakarta.servlet.http.HttpSession;

public class FileDelete {
	public boolean deleteFile(String filePath, HttpSession session) {
	    // ���� ���� ��θ� ������
	    String realPath = session.getServletContext().getRealPath("/resources/uploadFiles/");
	    File fileToDelete = new File(realPath + filePath);

	    // ������ �����ϴ��� Ȯ���ϰ� ����
	    if (fileToDelete.exists()) {
	        return fileToDelete.delete(); // ������ �����Ǹ� true ��ȯ
	    } else {
	        System.out.println("������ �������� �ʽ��ϴ�: " + filePath);
	        return false; // ������ ���� ��� false ��ȯ
	    }
	}
}
