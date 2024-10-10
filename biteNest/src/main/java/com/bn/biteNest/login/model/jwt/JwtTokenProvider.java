package com.bn.biteNest.login.model.jwt;

import java.sql.Date;
import java.util.Base64;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class JwtTokenProvider {

	// 비밀키
	private String secretKey = "secret";
	
	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}
	
	// JWT 토큰 생성
	public String createToken(String userId, String roles) {
		
		Claims claims = Jwts.claims().setSubject(userId);
		claims.put("roles", roles);	// 권한 추가
		
		Date now = new Date();
		Date validity = new Date(now.getTime() + 3600000);	// token 유효시간 1시간 제한
		
		return Jwts.builder()
						.setClaims(claims)
						.setIssuedAt(now)
						.setExpiration(validity)
						.signWith(SignatureAlgorithm.HS256, secretKey)
						.compact();
	}
	
	// Token에서 userId 추출
	public String getUserId(String token) {
		
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}
	
	// Token 유효성 검증
	public boolean validateToken(String token) {
		
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
