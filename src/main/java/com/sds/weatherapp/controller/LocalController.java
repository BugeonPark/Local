package com.sds.weatherapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sds.weatherapp.common.FileManager;
import com.sds.weatherapp.domain.AddrDong;
import com.sds.weatherapp.domain.Member;
import com.sds.weatherapp.domain.Story;
import com.sds.weatherapp.exception.StoryException;
import com.sds.weatherapp.model.local.StoryService;
import com.sds.weatherapp.model.map.MapAPIService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LocalController {
	
	@GetMapping("/local")
	public String getLocal() {
		
		return "local/story_index";
	}
	
	@GetMapping("/local/story")
	public String getStoryForm() {
		return "/local/story";
	}
	
	
	
}
