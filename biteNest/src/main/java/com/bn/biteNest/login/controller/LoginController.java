package com.bn.biteNest.login.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bn.biteNest.login.model.service.LoginService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;
	
	@PostMapping
	public ResponseEntity<?> login(@RequestBody Login login) {
		
	}
	
}
