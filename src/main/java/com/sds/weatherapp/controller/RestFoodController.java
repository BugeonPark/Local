package com.sds.weatherapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sds.weatherapp.domain.Description;
import com.sds.weatherapp.domain.Food;
import com.sds.weatherapp.domain.FoodDescription;
import com.sds.weatherapp.domain.FoodHumidity;
import com.sds.weatherapp.domain.FoodTaste;
import com.sds.weatherapp.domain.FoodTemp;
import com.sds.weatherapp.domain.Humidity;
import com.sds.weatherapp.domain.PreferCommentDoc;
import com.sds.weatherapp.domain.Taste;
import com.sds.weatherapp.domain.Temp;
import com.sds.weatherapp.exception.CommentsException;
import com.sds.weatherapp.exception.FoodException;
import com.sds.weatherapp.model.food.FoodService;
import com.sds.weatherapp.model.food.comments.CommentsService;

@RestController
public class RestFoodController {
	
	@Autowired
	private FoodService foodService;
	
	@Autowired
	private CommentsService commentsService;
	
	@PostMapping("/food/regist")	//rest api 동사 제거하기
	public ResponseEntity regist(Food food, 
			@RequestParam(name = "description_arr") int[] description_arr, 
			@RequestParam("humiditie_arr") int[] humiditie_arr, 
			@RequestParam("temp_arr") int[] temp_arr, 
			@RequestParam("taste_arr") int[] taste_arr) {
		
		List<FoodDescription> descriptions = new ArrayList<FoodDescription>();
		for(int i : description_arr) {
			Description d = new Description();
			d.setDescription_idx(i);
			FoodDescription fd = new FoodDescription();
			fd.setDescription(d);
			descriptions.add(fd);
		}
		List<FoodHumidity> humidities = new ArrayList<FoodHumidity>();
		for(int i : humiditie_arr) {
			Humidity h = new Humidity();
			h.setHumidity_idx(i);
			FoodHumidity fh = new FoodHumidity();
			fh.setHumidity(h);
			humidities.add(fh);
		}
		List<FoodTemp> temps = new ArrayList<FoodTemp>();
		for(int i : temp_arr) {
			Temp t = new Temp();
			t.setTemp_idx(i);
			FoodTemp ft = new FoodTemp();
			ft.setTemp(t);
			temps.add(ft);
		}
		List<FoodTaste> tastes = new ArrayList<FoodTaste>();
		for(int i : taste_arr) {
			Taste t = new Taste();
			t.setTaste_idx(i);
			FoodTaste ft = new FoodTaste();
			ft.setTaste(t);
			tastes.add(ft);
		}
		food.setDescriptions(descriptions);
		food.setHumidities(humidities);
		food.setTemps(temps);
		food.setTastes(tastes);
		
		foodService.regist(food);
		
		ResponseEntity entity = ResponseEntity.status(HttpStatus.OK).build();
		return entity;
	}
	
	@PostMapping("/food/prefer")
	public ResponseEntity registPrefer(@RequestBody List<PreferCommentDoc> preferComments) {
		for(PreferCommentDoc pc : preferComments) {
			commentsService.registComments(pc);
		}
		
		ResponseEntity entity = ResponseEntity.ok(preferComments.get(0));
		return entity;
	}
	
	@ExceptionHandler({FoodException.class, CommentsException.class})
	public ResponseEntity handle(FoodException e) {
		e.printStackTrace();
		ResponseEntity entity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		return entity;
	}
}
