package com.bn.biteNest.user.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.bn.biteNest.user.model.vo.User;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Integer> {

	
	
}
