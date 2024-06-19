package com.sds.weatherapp.model.food;

import org.apache.ibatis.annotations.Mapper;

import com.sds.weatherapp.domain.FoodHumidity;
import com.sds.weatherapp.domain.FoodTaste;
import com.sds.weatherapp.domain.FoodTemp;

@Mapper
public interface FoodTasteDAO {
	public int insert(FoodTaste foodTaste);
	
	public FoodTaste selectByFoodIdx(int food_idx);
}
