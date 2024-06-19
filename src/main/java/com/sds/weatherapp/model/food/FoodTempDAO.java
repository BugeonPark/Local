package com.sds.weatherapp.model.food;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.weatherapp.domain.FoodTemp;

@Mapper
public interface FoodTempDAO {
	public int insert(FoodTemp foodTemp);
	
	public List<FoodTemp> selectByFoodIdx(int food_idx);
}
