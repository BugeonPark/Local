//package com.sds.weatherapp;
//
//import java.io.IOException;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
//
//import com.sds.weatherapp.domain.CustomUserDetails;
//import com.sds.weatherapp.domain.Member;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class LoginEventHandler extends SavedRequestAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler{
//
//	@Override
//	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//			Authentication authentication) throws ServletException, IOException {
//		
//		log.debug("로그인 성공");
//		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
//		Member member = userDetails.getMember();
//		
//		HttpSession session = request.getSession();
//		session.setAttribute("member", member);
//		
//		super.onAuthenticationSuccess(request, response, authentication);
//	}
//	
//	
//}
