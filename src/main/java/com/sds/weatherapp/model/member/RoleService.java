package com.sds.weatherapp.model.member;

import com.sds.weatherapp.domain.Role;

public interface RoleService {
	public Role selectByName(String role_name);
}
