package com.bn.biteNest.common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

public class FileUpload {
	public String saveImage(MultipartFile upfile, HttpSession session, String recipeName, Integer stepNumber) {

	    String originName = upfile.getOriginalFilename();  // ���� ���ϸ� ��������
	    String ext = originName.substring(originName.lastIndexOf("."));// ���� Ȯ���� ����
	    int num = (int) (Math.random() * 900) + 100;// ���ϸ� ���� ���� ���� ���� (100 ~ 999 ������ ��)
	    String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());// ���� �ð� ���� (yyyyMMddHHmmss)
	    String formattedRecipeName = recipeName.replaceAll(" ", "_");// ������ �̸����� ������ "_"�� ��ȯ

	    String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/");// ���� ���� ��� ����

	    String changeName;// ���ϸ� ���� (���� �̹����� ��� ���� ��ȣ�� ����)
	    if (stepNumber == null) { 
	        changeName = "BN_" + currentTime + "_" + formattedRecipeName + "_" + num + ext;// ���� �̹��� ���ϸ� ����: BN_currentTime_�������̸�_num_ext
	    } else {
	        changeName = "BN_" + currentTime + "_" + formattedRecipeName + "_step" + stepNumber + "_" + num + ext;// ���� �̹��� ���ϸ� ����: BN_currentTime_�������̸�_���ǹ�ȣ_num_ext
	    }

	    try {
	        upfile.transferTo(new File(savePath + changeName)); // ������ �ش� ��ο� ����
	    } catch (IllegalStateException | IOException e) {
	        e.printStackTrace();
	    }

	    return savePath + changeName;  // ����� ������ ��ü ��� ��ȯ
	}
}
