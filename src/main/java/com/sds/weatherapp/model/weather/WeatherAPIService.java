package com.sds.weatherapp.model.weather;

import com.sds.weatherapp.domain.WeatherInfo;

public interface WeatherAPIService {
	public WeatherInfo getWeatherData(String city);
}	
