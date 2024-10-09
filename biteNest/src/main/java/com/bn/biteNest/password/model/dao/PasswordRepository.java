package com.bn.biteNest.password.model.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bn.biteNest.password.model.vo.Password;

public interface PasswordRepository extends JpaRepository<Password, Integer> {

	Optional<Password> findByUserUserNo(int userNo);
	
}
