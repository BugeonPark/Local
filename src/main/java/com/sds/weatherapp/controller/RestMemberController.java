package com.sds.weatherapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.sds.weatherapp.domain.Member;
import com.sds.weatherapp.jwt.JwtvalidService;
import com.sds.weatherapp.sns.KaKaoLogin;
import com.sds.weatherapp.sns.NaverLogin;

import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
public class RestMemberController {
	
	@Autowired
	private NaverLogin naverLogin;
	@Autowired
	private KaKaoLogin kaKaoLogin;
	@Autowired
	private JwtvalidService jwtvalidService;
	//로그인 요청에 필요한 링크 주소 및 파라미터 생성 요청 처리
	@GetMapping("/rest/member/authform/{sns}")
	public ResponseEntity getLink(@PathVariable("sns") String sns) {
		
		ResponseEntity entity=null;
		System.out.println("호출");
		
		if(sns.equals("google")) {
		}else if(sns.equals("naver")) {
			entity=ResponseEntity.ok(naverLogin.getGrantUrl());  //내용을 보내야 하므로, body도 구성하자
		}else if(sns.equals("kakao")) {
			entity=ResponseEntity.ok(kaKaoLogin.getGrantUrl());
		}
		return entity; 
	}
	
	@GetMapping("/rest/member/logincheck")
	public ResponseEntity getLoginMember(@RequestHeader("Authorization") String header) {
		
		log.debug("토큰 검증 요청"+header);
		//넘어온 헤더값은 "Bearer 토큰값"인데, 순수 토큰만을 추출
		String token = header.replace("Bearer ", "");
		
		Member member = jwtvalidService.getMemberFromJwt(token);
		
		ResponseEntity entity = ResponseEntity.ok(member);
		
		return entity;
	}
}
