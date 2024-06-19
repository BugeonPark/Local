package com.sds.weatherapp.model.food;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.weatherapp.domain.Taste;

@Mapper
public interface TasteDAO {
	public List<Taste> selectAll();
	public Taste selectByIdx(int taste_idx);
}
