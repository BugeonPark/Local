package com.sds.weatherapp.model.food;

import org.apache.ibatis.annotations.Mapper;

import com.sds.weatherapp.domain.FoodDescription;

@Mapper
public interface FoodDescriptionDAO {
	public int insert(FoodDescription foodDescription);
}
