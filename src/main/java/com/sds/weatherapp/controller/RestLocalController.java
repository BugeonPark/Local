package com.sds.weatherapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sds.weatherapp.domain.AddrDong;
import com.sds.weatherapp.domain.Member;
import com.sds.weatherapp.domain.Story;
import com.sds.weatherapp.exception.StoryException;
import com.sds.weatherapp.model.local.StoryService;
import com.sds.weatherapp.model.map.MapAPIService;

import jakarta.servlet.http.HttpSession;

@RestController
public class RestLocalController {
	
	@Autowired
	private MapAPIService mapAPIService;
	@Autowired
	private StoryService storyService;
	
	@PostMapping("/local/story")
	public ResponseEntity registStory(HttpSession session, Story story) {
		Member member = (Member)session.getAttribute("member");
		story.setMember(member);
		
		AddrDong dto = mapAPIService.getDong(story);
		story.setDong(dto.getDocuments().get(0).getRegion_3depth_name());
		
		storyService.regist(story);
		
		ResponseEntity entity = ResponseEntity.ok(dto.getDocuments());
		return entity;
	}
	
	@GetMapping("/local/show")
	public ResponseEntity showPlaces(HttpSession session) {
		
		Member member = (Member)session.getAttribute("member");
		List<Story> list = storyService.getPlaces(member);
		ResponseEntity entity = ResponseEntity.ok(list);
		return entity;
		
	}
	
	@GetMapping("/local/place")
	public ResponseEntity showList(@RequestParam(value = "place_name") String place_name) {
		List<Story> list = storyService.getStories(place_name);
		ResponseEntity entity = ResponseEntity.ok(list);
		return entity;
	}
	
	@ExceptionHandler(StoryException.class)
	public ResponseEntity handle(StoryException e) {
		e.printStackTrace();
		ResponseEntity entity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		return entity;
	}

	
}
