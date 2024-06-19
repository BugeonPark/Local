package com.sds.weatherapp.jwt;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.weatherapp.domain.Member;
import com.sds.weatherapp.exception.JwtException;
import com.sds.weatherapp.model.member.MemberService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtvalidService {
	@Autowired
	private MemberService memberService;
	@Autowired
	private KeyService keyService;
	@Autowired
	private JwtUtil jwtUtil;
	
	public Member getMemberFromJwt(String token) {
		
		String publicKey = keyService.getPublicKey();
		
		Claims claims = null;
		try {
			claims = Jwts.parser().setSigningKey(jwtUtil.getPublicKeyFromString(publicKey)).parseClaimsJws(token).getBody();
			
		} catch (Exception e) {
			log.debug("JWT 인증 실패");
			throw new JwtException("Jwt 인증 실패");
		}
		String uid = claims.getSubject();
		Member member = memberService.selectByUid(uid);
		return member;
	}
}
