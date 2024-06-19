package com.sds.weatherapp.model.member;

import org.apache.ibatis.annotations.Mapper;

import com.sds.weatherapp.domain.Sns;

@Mapper
public interface SnsDAO {
	public Sns selectByName(String sns_name);
	public Sns select(int sns_idx);
}
