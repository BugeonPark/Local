package com.sds.weatherapp.model.member;

import com.sds.weatherapp.domain.Sns;

public interface SnsService {
	public Sns selectByName(String sns_name);
}
