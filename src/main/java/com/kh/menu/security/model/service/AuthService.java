package com.kh.menu.security.model.service;

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
		
		
		return null;
	}

}









