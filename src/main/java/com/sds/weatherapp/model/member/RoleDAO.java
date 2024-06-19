package com.sds.weatherapp.model.member;

import org.apache.ibatis.annotations.Mapper;

import com.sds.weatherapp.domain.Role;

@Mapper
public interface RoleDAO {
	public Role selectByName(String role_name);
	public Role select(int role_idx);
}
