package com.bn.biteNest.user.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Signup {

	// User
	private String userId;
	private String loginType;
	
	// Profile
	private String nickname;
	private String imageUrl;
	private String introduction;
	
	// Password
	private String password;
}
