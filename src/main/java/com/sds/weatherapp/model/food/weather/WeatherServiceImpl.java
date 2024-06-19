package com.sds.weatherapp.model.food.weather;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.weatherapp.domain.Description;
import com.sds.weatherapp.domain.Humidity;
import com.sds.weatherapp.domain.Temp;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WeatherServiceImpl implements WeatherService{
	@Autowired
	private DescriptionDAO descriptionDAO;
	@Autowired
	private HumidityDAO humidityDAO;
	@Autowired
	private TempDAO tempDAO;
	
	public List<Description> getDescription(){
		
		return descriptionDAO.selectAll();
	}
	
	@Override
	public List<Humidity> getHumidity() {
		return humidityDAO.selectAll();
	}
	
	@Override
	public List<Temp> getTemp() {
		return tempDAO.selectAll();
	}
	
}
