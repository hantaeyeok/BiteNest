package com.bn.biteNest.password.model.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bn.biteNest.password.model.dao.PasswordRepository;
import com.bn.biteNest.password.model.vo.Password;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {

	private final PasswordRepository passwordRepository;
	
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	// 비밀번호 암호화(회원가입)
	@Override
	public Password savePassword(Password password) {
		
		password.setPassword(passwordEncoder.encode(password.getPassword()));
		password.setUpdateDate(LocalDateTime.now());
		
		return passwordRepository.save(password);
	}

	// 비밀번호 조회(userNo)
	@Override
	public Optional<Password> findByUserNo(int userNo) {
		
		return passwordRepository.findByUserUserNo(userNo);
	}

	// 비밀번호 업데이트
	@Override
	public Password updatePassword(Password password, String newPassword) {
		
		password.setPassword(passwordEncoder.encode(newPassword));
		password.setUpdateDate(LocalDateTime.now());
		
		return passwordRepository.save(password);
	}
	

	// 비밀번호 검증(로그인)
	@Override
	public boolean checkPassword(Password password, String rawPassword) {
		
		return passwordEncoder.matches(password.getPassword(), rawPassword);
	}

}
