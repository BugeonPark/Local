package com.sds.weatherapp.model.food.weather;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.weatherapp.domain.Description;

@Mapper
public interface DescriptionDAO {
	public List<Description> selectAll();
}
