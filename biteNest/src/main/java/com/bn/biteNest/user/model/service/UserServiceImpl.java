package com.bn.biteNest.user.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bn.biteNest.user.model.dao.UserMapper;
import com.bn.biteNest.user.model.dao.UserRepository;
import com.bn.biteNest.user.model.vo.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserMapper userMapper;
	private final UserRepository userRepository;
	
	@Override
	public User save(User user) {
		return userRepository.save(user);
	}
	
	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	@Override
	public Optional<User> findById(int id) {
		return userRepository.findById(id);
	}
	
	@Override
	public void delete(User user) {
		userRepository.delete(user);
	}
	
}
