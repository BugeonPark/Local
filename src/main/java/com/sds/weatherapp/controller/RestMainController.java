package com.sds.weatherapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sds.weatherapp.domain.Food;
import com.sds.weatherapp.domain.Member;
import com.sds.weatherapp.domain.Restaurant;
import com.sds.weatherapp.domain.Urls;
import com.sds.weatherapp.domain.UserLocation;
import com.sds.weatherapp.domain.WeatherInfo;
import com.sds.weatherapp.model.food.recommend.RecommendService;
import com.sds.weatherapp.model.image.ImgAPIService;
import com.sds.weatherapp.model.map.MapAPIService;
import com.sds.weatherapp.model.weather.WeatherAPIService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RestMainController {

	@Autowired
	private WeatherAPIService weatherAPIService;
	@Autowired
	private RecommendService recommendService;
	@Autowired
	private MapAPIService mapAPIService;
	@Autowired
	private ImgAPIService imgAPIService;

	@GetMapping("/rest/show")
	public ResponseEntity show(HttpSession session, @RequestParam(value = "index", defaultValue = "0") int index) {
		
		log.debug("넘겨받은 index : " + index);
		Member member = (Member) session.getAttribute("member");
		System.out.println(member);
		WeatherInfo weatherInfo = new WeatherInfo();
		weatherInfo = weatherAPIService.getWeatherData(member.getMemberDetail().getSidoEnglish());

		Map map = new HashMap();
		map.put("description_idx", weatherInfo.getDescription_idx());
		map.put("temp_idx", weatherInfo.getTemp_idx());
		map.put("humidity_idx", weatherInfo.getHumidity_idx());
		log.debug("추천 로직 시작 ");

		List<Food> foodList = recommendService.getList(member, map);
		
		weatherInfo.setFoodList(foodList);
		
		UserLocation userLocation = mapAPIService.getUserLocation(member.getMemberDetail());
		weatherInfo.setUserLocation(userLocation);
		log.debug("사용자 위치x : " + weatherInfo.getUserLocation().getDocuments().get(0).getX());
		log.debug("사용자 위치y : " + weatherInfo.getUserLocation().getDocuments().get(0).getY());
		
		String x = userLocation.getDocuments().get(0).getX();
		String y = userLocation.getDocuments().get(0).getY();
		
		String keywordName = foodList.get(0).getFoodCategory().getFood_category_name() + " 음식"; // 일식으로 검색 시 개기일식이 나옴. -> 일식 음식으로 검색해야 함.
		Urls urls = imgAPIService.getImage(keywordName);
		
		weatherInfo.setRestaurant(mapAPIService.getResaurant(x, y, foodList.get(index).getFood_name()));
		weatherInfo.getRestaurant().setUrls(urls);
		
		System.out.println("최초 요청시 응답 결과 : " + weatherInfo);
		ResponseEntity entity = ResponseEntity.ok(weatherInfo);

		return entity;
		//여러 서비스들을처리하는 하나의 서비스 레이어를 더 두기
		//result.
	}
	
	@GetMapping("/rest/search")
	public ResponseEntity search(HttpSession session, 
			@RequestParam("x") String x,
			@RequestParam("y") String y,
			@RequestParam("food_name")  String food_name,
			@RequestParam("food_category_name") String food_category_name
			) {
		log.debug("음식검색만 하는 버튼에서 가져온 x : " + x);
		log.debug("음식검색만 하는 버튼에서 가져온 y : " + y);
		log.debug(food_category_name);
		Member member = (Member) session.getAttribute("member");
		Restaurant restaurant = mapAPIService.getResaurant(x,y, food_name);
		restaurant.setUrls(imgAPIService.getImage(food_category_name + " 음식"));
		log.debug("검색 결과 : " + restaurant);
		ResponseEntity entity = ResponseEntity.ok(restaurant);

		return entity;
	}
}