package com.sds.weatherapp.model.food;

import java.util.List;
import java.util.Map;

import com.sds.weatherapp.domain.Food;
import com.sds.weatherapp.domain.Member;

public interface FoodService {
	public void regist(Food food);
	public List selectAll();
	public List selectByWeather(Member member, Map map, Food favorite);
}
