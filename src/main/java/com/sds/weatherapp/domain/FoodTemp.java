package com.sds.weatherapp.domain;

import lombok.Data;

@Data
public class FoodTemp {
	private int food_temp_idx;
	private Food food;
	private Temp temp;
}
