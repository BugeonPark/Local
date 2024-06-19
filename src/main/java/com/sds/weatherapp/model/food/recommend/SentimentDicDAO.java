package com.sds.weatherapp.model.food.recommend;

import com.sds.weatherapp.domain.SentimentDic;

public interface SentimentDicDAO {
	public SentimentDic select(String word);
}
