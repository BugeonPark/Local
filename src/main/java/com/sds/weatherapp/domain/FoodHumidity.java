package com.sds.weatherapp.domain;

import lombok.Data;

@Data
public class FoodHumidity {
	private int food_humidity_idx;
	private Food food;
	private Humidity humidity;
}
