package com.sds.weatherapp.model.food.recommend;

import java.util.List;
import java.util.Map;

import com.sds.weatherapp.domain.Member;

public interface RecommendService {
	public List getList(Member member, Map map);
}
