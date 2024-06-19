package com.sds.weatherapp.model.food;

import org.apache.ibatis.annotations.Mapper;

import com.sds.weatherapp.domain.FoodHumidity;

@Mapper
public interface FoodHumidityDAO {
	public int insert(FoodHumidity foodHumidity);
}
