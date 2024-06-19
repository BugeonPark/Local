package com.sds.weatherapp.model.food.weather;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.weatherapp.domain.Temp;

@Mapper
public interface TempDAO {
	public List<Temp> selectAll();
}
