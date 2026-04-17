package com.kh.menu.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.menu.security.model.dto.AuthDto.AuthResult;
import com.kh.menu.security.model.dto.AuthDto.LoginRequest;
import com.kh.menu.security.model.provider.JWTProvider;
import com.kh.menu.security.model.service.AuthService;
import com.kh.menu.security.model.service.KakaoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {
	
	private final AuthService service;
	private final KakaoService kakaoService;
	private final JWTProvider jwt;
	
	@PostMapping("/login")
	public ResponseEntity<AuthResult> login(@RequestBody LoginRequest req){
		/* 
		 * 로그인 
		 *  - 현재 db에 존재하지 않는 이메일이면 404에러 반환
		 *  - 프런트에서는 응답상태가 404인 경우 회원가입할지, 재로그인할지 처리
		 *  - 이멜은 존재하나 비밀번호가 틀린경우 401상태(미인증)반환 => 재로그인
		 *  - 모두 성공시 유저정보와 JWT토큰 반환
		 * */
		// 1) 사용자가 존재하는지 확인
		boolean exists = service.existsByEmail(req.getEmail());
		
		if(!exists) {
			// 2) 사용자가 존재하지 않는 경우 404상태 반환
			return ResponseEntity.notFound().build();
		}
		// 3) 사용자가 존재한다면 인증처리하고, 인증정보 반환
		try {
			AuthResult result = service.login(req.getEmail(), req.getPassword());
			
			// 인증성공시 accessToken, refreshToken생성하여 클라이언트의 쿠키로 전달.
			return ;
		}catch(BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
}






