package com.bn.biteNest.profile.model.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bn.biteNest.profile.model.dao.ProfileRepository;
import com.bn.biteNest.profile.model.vo.Profile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

	private final ProfileRepository profileRepository;
	
	@Override
	public Optional<Profile> findById(int id) {
		return profileRepository.findById(id);
	}

	@Override
	public Profile save(Profile profile) {
		return profileRepository.save(profile);
	}

	@Override
	public void deleteById(int id) {
		
	}

}
