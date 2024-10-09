package com.bn.biteNest.profile.model.service;

import java.util.Optional;

import com.bn.biteNest.profile.model.vo.Profile;

public interface ProfileService {

	Optional<Profile> findById(int id);

	Profile save(Profile profile);

	void deleteById(int id);

}
