package com.bn.biteNest.password.model.vo;

import java.time.LocalDateTime;

import com.bn.biteNest.user.model.vo.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "AUTH_PASSWORD_TB")
public class Password {

	@Id
	private int passwordId;
	
	@OneToOne
	@JoinColumn(name = "USER_NO", nullable = false)
	private User user;
	
	private String salt;
	private String password;
	private LocalDateTime updateDate;
	
}