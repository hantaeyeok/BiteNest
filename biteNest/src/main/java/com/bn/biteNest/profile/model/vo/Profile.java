package com.bn.biteNest.profile.model.vo;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.bn.biteNest.user.model.vo.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
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
@Table(name = "PROFILE_TB")
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_seq")
    @SequenceGenerator(name = "profile_seq", sequenceName = "PROFILE_SEQ", allocationSize = 1)
	private int profileId;
	
	@OneToOne
	@JoinColumn(name = "USER_NO", nullable = false)
	private User user;
	
	
	private String nickname;
	private String imageUrl;
	private String introduction;
	
	@CreatedDate
	private LocalDateTime joinDate;
	
	@LastModifiedDate
	private LocalDateTime updateDate;
	
}
