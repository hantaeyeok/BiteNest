package com.bn.biteNest.password.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bn.biteNest.password.model.service.PasswordService;
import com.bn.biteNest.password.model.vo.Password;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/password")
@RequiredArgsConstructor
public class PasswordController {

	private final PasswordService passwordService;
	
	// 비밀번호 생성
	@PostMapping("/create")
	public ResponseEntity<Password> createPassword(@RequestBody Password password) {
		Password createdPassword = passwordService.createPassword(password);
		
		return ResponseEntity.ok(createdPassword);
	}
	
	// 비밀번호 업데이트
	@PutMapping("/update/{userNo}")
	public ResponseEntity<Password> updatePassword(@PathVariable int userNo,
																		   @RequestBody String newPassword) {
		
		Optional<Password> passwordOptional = passwordService.findByUserNo(userNo);
		
		if(passwordOptional.isPresent()) {
			
			Password updatedPassword = passwordService.updatePassword(passwordOptional.get(), newPassword);
			
			return ResponseEntity.ok(updatedPassword);
			
		} else {
			return ResponseEntity.notFound()
										  .build();
		}
	}
	
	// 비밀번호 검증
	@PostMapping("/check")
	public ResponseEntity<Boolean> checkPassword(@RequestParam int userNo,
																	   @RequestBody String rawPassword) {
		
		Optional<Password> passwordOptional = passwordService.findByUserNo(userNo);
		if(passwordOptional.isPresent()) {
			boolean matches = passwordService.checkPassword(passwordOptional.get(), rawPassword);
			return ResponseEntity.ok(matches);
		} else {
			return ResponseEntity.notFound()
										  .build();
		}
	}
	
}
