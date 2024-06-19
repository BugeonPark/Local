package com.sds.weatherapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sds.weatherapp.domain.Description;
import com.sds.weatherapp.domain.FoodCategory;
import com.sds.weatherapp.domain.Humidity;
import com.sds.weatherapp.domain.Taste;
import com.sds.weatherapp.domain.Temp;
import com.sds.weatherapp.model.food.FoodTypeService;
import com.sds.weatherapp.model.food.weather.WeatherService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class FoodController {
	
	@Autowired
	private WeatherService weatherService;
	@Autowired
	private FoodTypeService tasteService;
	
	@GetMapping("/food/regist")
	public String getForm(Model model) {
		
		List<Description> descriptionList = weatherService.getDescription();
		List<Humidity> humidityList = weatherService.getHumidity();
		List<Temp> tempList = weatherService.getTemp();
		List<Taste> tasteList = tasteService.getTaste();
		List<FoodCategory> categoryList = tasteService.getCategory();
		
		System.out.println("tasteList size : " + tasteList.size());
		
		model.addAttribute("descriptionList", descriptionList);
		model.addAttribute("humidityList", humidityList);
		model.addAttribute("tempList", tempList);
		model.addAttribute("tasteList", tasteList);
		model.addAttribute("categoryList", categoryList);
		
		return "admin/food/regist";
	}
	
	@GetMapping("/food/prefer")
	public String getPreferForm(Model model) {
		
		List<FoodCategory> categoryList = tasteService.getCategory();
		model.addAttribute("categoryList", categoryList);
		
		return "user/food/preference";
	}
}
