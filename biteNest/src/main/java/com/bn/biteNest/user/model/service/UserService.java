package com.bn.biteNest.user.model.service;

import java.util.List;
import java.util.Optional;

import com.bn.biteNest.user.model.vo.User;

public interface UserService {

	User save(User user);

	List<User> findAll();

	Optional<User> findById(int id);

	void delete(User user);

	

	
	
	
}
