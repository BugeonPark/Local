package com.sds.weatherapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sds.weatherapp.domain.Address;
import com.sds.weatherapp.domain.Food;
import com.sds.weatherapp.domain.Member;
import com.sds.weatherapp.domain.MemberUpdate;
import com.sds.weatherapp.domain.Role;
import com.sds.weatherapp.exception.MemberException;
import com.sds.weatherapp.model.food.FoodService;
import com.sds.weatherapp.model.member.MemberService;
import com.sds.weatherapp.sns.OAuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberController {
	
	@Autowired
	private FoodService foodService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private OAuthService oAuthService;
	
	@GetMapping("/member/loginform")
	public String getLoginForm() {
		
		return "member/login";
	}
	
	@PostMapping("/member/login")
	public String login(Member member
//			, HttpSession session
//			, @RequestParam(name="password") String password
			) {
//		Member dto = memberService.login(member);
//		session.setAttribute("member", dto);
		
		System.out.println("로그인 멤버 : " + member);
		return "redirect:/";
	}
	
	@GetMapping("/member/registform")
	public String getRegistForm(Model model) {
		
		List<Food> foodList = foodService.selectAll();
		model.addAttribute("foodList", foodList);
		return "/member/regist";
	}
	
	@PostMapping("/member/regist")
	public String join(Member member) {
		System.out.println(member);
		Role role = new Role();
		role.setRole_name("USER");
		member.setRole(role);
		memberService.regist(member);
		
		
		return "redirect:/";
	}
	
	@GetMapping("/member/update")
	public String getUpdate(Model model) {
		List<Food> foodList = foodService.selectAll();
		model.addAttribute("foodList", foodList);
		return "/member/update";
	}
	
	@PostMapping("/member/update")
	public String update(MemberUpdate memberUpdate, HttpSession session) {
		Member member = (Member)session.getAttribute("member");
		Member dto = memberService.update(member, memberUpdate);
		session.setAttribute("member", dto);
		return "redirect:/";
	}
	
	@GetMapping("/member/sns/naver/callback")
	public ModelAndView naverCallback(HttpServletRequest request) {
		
		oAuthService.naverLogin(request);
		
		ModelAndView mav = new ModelAndView("redirect:/");
		return mav;
	}
	
	@GetMapping("/member/sns/kakao/callback")
	public ModelAndView kakaoCallback(HttpServletRequest request) {
		oAuthService.kakaoLogin(request);
		ModelAndView mav = new ModelAndView("redirect:/");
		return mav;
	}
	
	@ExceptionHandler(MemberException.class)
	public ModelAndView handle(MemberException e) {
		ModelAndView mav = new ModelAndView("error/result");
		mav.addObject("e", e);
		return mav;
	}
}
