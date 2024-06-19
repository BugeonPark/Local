package com.sds.weatherapp.model.food;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.sds.weatherapp.domain.Food;

@Mapper
public interface FoodDAO {
	public int insert(Food food);
	public List selectAll();
	public Food selectByFoodIdx(int food_idx);
	public List selectByWeather(Map map);
}
