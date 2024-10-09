package com.bn.biteNest.profile.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.bn.biteNest.profile.model.vo.Profile;

@EnableJpaRepositories
public interface ProfileRepository extends JpaRepository<Profile, Integer> {

}
