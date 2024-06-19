package com.sds.weatherapp.model.food.weather;

import java.util.List;

import com.sds.weatherapp.domain.Description;
import com.sds.weatherapp.domain.Humidity;
import com.sds.weatherapp.domain.Temp;

public interface WeatherService {
	public List<Description> getDescription();
	public List<Humidity> getHumidity();
	public List<Temp> getTemp();
}
