package com.sds.weatherapp.model.food.weather;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.weatherapp.domain.Humidity;

@Mapper
public interface HumidityDAO {
	public List<Humidity> selectAll();
}
