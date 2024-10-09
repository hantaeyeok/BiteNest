package com.bn.biteNest.user.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bn.biteNest.password.model.service.PasswordService;
import com.bn.biteNest.password.model.vo.Password;
import com.bn.biteNest.profile.model.dao.ProfileRepository;
import com.bn.biteNest.profile.model.vo.Profile;
import com.bn.biteNest.user.model.dao.UserRepository;
import com.bn.biteNest.user.model.vo.Signup;
import com.bn.biteNest.user.model.vo.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final ProfileRepository profileRepository;
	private final PasswordService passwordService;
	
	@Override
	@Transactional
	public User regist(Signup signup) {
		
		// 1. User Entity 저장
		User user = User.builder()
							    .userId(signup.getUserId())
							    .loginType(signup.getLoginType())
							    .build();
		userRepository.save(user);
		
		// 2. Profile Entity 저장
		Profile profile = Profile.builder()
									   .user(user)
									   .nickname(signup.getNickname())
									   .imageUrl(signup.getImageUrl())
									   .introduction(signup.getIntroduction())
									   .build();
		profileRepository.save(profile);
		
		// 3. Password Entity 저장
		Password password = Password.builder()
												    .user(user)
												    .password(signup.getPassword())
												    .build();
		passwordService.savePassword(password);
		
		return user;
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

	@Override
	public User update(User updatedUser) {
		return userRepository.save(updatedUser);
	}


	
	
}
