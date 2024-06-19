package com.sds.weatherapp.sns;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.weatherapp.domain.Member;
import com.sds.weatherapp.model.member.MemberService;
import com.sds.weatherapp.model.member.RoleService;
import com.sds.weatherapp.model.member.SnsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OAuthService {
	
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private NaverLogin naverLogin;
	@Autowired
	private KaKaoLogin kaKaoLogin;
	@Autowired
	private RoleService roleService;
	@Autowired
	private SnsService snsService;
	@Autowired
	private MemberService memberService;
	
	public void naverLogin(HttpServletRequest request) {
		String code = request.getParameter("code");
		
		String token_url = naverLogin.getToken_request_url();
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		
		params.add("code", code);
		params.add("client_id", naverLogin.getClient_id());
		params.add("client_secret", naverLogin.getClient_secret());
		params.add("redirect_uri", naverLogin.getRedirect_uri());
		params.add("grant_type", naverLogin.getGrant_type());
		params.add("state", naverLogin.getState());
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/x-www-form-urlencoded");
		
		HttpEntity entity = new HttpEntity(params,headers);
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(token_url, HttpMethod.POST, entity, String.class);
		
		String body = responseEntity.getBody();
		log.debug("네이버가 보낸 인증 정보 : " + body);
		
		ObjectMapper objectMapper = new ObjectMapper();
		NaverOAuthToken oAuthToken = null;
		
		try {
			oAuthToken = objectMapper.readValue(body, NaverOAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		String userinfo_url = naverLogin.getUserinfo_url();
		headers.clear();
		headers.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
		
		HttpEntity entity2 = new HttpEntity(headers);
		ResponseEntity<String> userEntity = restTemplate.exchange(userinfo_url, HttpMethod.GET, entity2, String.class);
		
		String userBody = userEntity.getBody();
		HashMap<String, Object> userMap = null;
		try {
			userMap = objectMapper.readValue(userBody, HashMap.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		Map<String, String> response = (Map) userMap.get("response");
		log.debug("응답 결과 매핑 후 : " + response);
		String id = response.get("id");
		String name = response.get("name");
		
		Member member = new Member();
		member.setUid(id);
		member.setNickname(name);
		member.setSns(snsService.selectByName("naver"));
		member.setRole(roleService.selectByName("USER"));
		Member dto = memberService.selectByUid(id);
		if(dto == null) {
			memberService.regist(member);
//			dto = member;
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("member", dto);
		System.out.println("네이버 로그인 멤버 : " + dto);
		
		Authentication auth = new UsernamePasswordAuthenticationToken(member.getNickname(), null, Collections.singletonList(new SimpleGrantedAuthority("USER")));
		SecurityContextHolder.getContext().setAuthentication(auth);
		session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
		
	}
	
	public void kakaoLogin(HttpServletRequest request) {
		String code = request.getParameter("code");
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("code", code);
		params.add("client_id", kaKaoLogin.getClient_id());
		params.add("redirect_uri", kaKaoLogin.getRedirect_uri());
		params.add("grant_type", kaKaoLogin.getGrant_type());
		
		log.debug("kakao params : " + params);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/x-www-form-urlencoded");
		HttpEntity entity = new HttpEntity(params, headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange(kaKaoLogin.getToken_request_url(), HttpMethod.POST, entity, String.class);
		String body = responseEntity.getBody();
		log.debug("kakao 토큰 응답 정보 : "  +body);
		
		ObjectMapper objectMapper = new ObjectMapper();
		KaKaoOAuthToken oAuthToken = null;
		try {
			oAuthToken = objectMapper.readValue(body, KaKaoOAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		headers.clear();
		headers.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
		HttpEntity entity2 = new HttpEntity(headers);
		
		ResponseEntity<String> responseEntity2 = restTemplate.exchange(kaKaoLogin.getUserinfo_url(), HttpMethod.GET, entity2, String.class);
		String userInfo = responseEntity2.getBody();
		log.debug(userInfo);
		
		HashMap<String, Object> userMap = null;
		try {
			userMap = objectMapper.readValue(userInfo, HashMap.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		Map<String,String> nameMap = (Map)userMap.get("properties");
		String nickname = nameMap.get("nickname");
		String id = Long.toString((Long)userMap.get("id"));
		
		Member member = new Member();
		member.setUid(id);
		member.setNickname(nickname);
		member.setSns(snsService.selectByName("kakao"));
		member.setRole(roleService.selectByName("USER"));
		
		Member dto = memberService.selectByUid(id);
		if(dto == null) {
			memberService.regist(member);
			dto = member;
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("member", dto);
		
		Authentication auth = new UsernamePasswordAuthenticationToken(member.getNickname(), null, Collections.singletonList(new SimpleGrantedAuthority("USER")));
		SecurityContextHolder.getContext().setAuthentication(auth);
		session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
		
	}
	
}