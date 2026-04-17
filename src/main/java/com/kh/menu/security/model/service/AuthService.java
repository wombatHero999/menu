package com.kh.menu.security.model.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.menu.security.model.dao.AuthDao;
import com.kh.menu.security.model.dto.AuthDto.AuthResult;
import com.kh.menu.security.model.dto.AuthDto.User;
import com.kh.menu.security.model.provider.JWTProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
	
	private final AuthDao authDao;
	private final PasswordEncoder encoder; // bean객체 securityConfig에만들기
	private final KakaoService service;
	private final JWTProvider jwt;
	
	
	public boolean existsByEmail(String email) {
		User user = authDao.findUserByEmail(email);
		return user != null;
	}


	public AuthResult login(String email, String password) {
		// 1. 사용자 정보 조회
		User user = authDao.findUserByEmail(email);
		
		if(!encoder.matches(password, user.getPassword())) {
			throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
		}
		
		// 2) 토큰발급
		String accessToken = jwt.createAccessToken(user.getId() , 30);
		String refreshToken = jwt.createRefreshToken(user.getId(), 7);
		
		User userNoPassword = User
								.builder()
								.id(user.getId())
								.email(user.getEmail())
								.profile(user.getProfile())
								.name(user.getName())
								.roles(user.getRoles())
								.build();
		return AuthResult.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.user(userNoPassword)
				.build();
	}

}









