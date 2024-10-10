package com.bn.biteNest.login.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bn.biteNest.login.model.service.LoginService;
import com.bn.biteNest.login.model.vo.LoginRequest;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;
	
	@PostMapping
	public ResponseEntity<?> login(@RequestBody LoginRequest loginReqeust) {
		
		String token = loginService.login(loginRequest);
		return ResponseEntity.ok(new JwtResponse(token));
	}
	
}
