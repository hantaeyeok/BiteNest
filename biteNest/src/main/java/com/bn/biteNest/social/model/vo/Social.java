package com.bn.biteNest.social.model.vo;

import java.time.LocalDateTime;

import com.bn.biteNest.user.model.vo.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "AUTH_SOCIAL_LOGIN_TB")
public class Social {

	@Id
	private int socialLoginId;
	
	@ManyToOne
	@JoinColumn(name = "USER_NO", nullable = false)
	private User user;
	
	private int socialCode;
	private String externalId;
	private String accessToken;
	private LocalDateTime updateDate;
	
}