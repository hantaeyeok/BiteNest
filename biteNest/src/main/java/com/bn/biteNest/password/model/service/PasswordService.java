package com.bn.biteNest.password.model.service;

import java.util.Optional;

import com.bn.biteNest.password.model.vo.Password;

public interface PasswordService {

	Password createPassword(Password password);

	Optional<Password> findByUserNo(int userNo);

	Password updatePassword(Password password, String newPassword);

	boolean checkPassword(Password password, String rawPassword);

}
