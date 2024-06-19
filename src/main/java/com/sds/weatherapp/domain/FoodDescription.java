package com.sds.weatherapp.domain;

import lombok.Data;

@Data
public class FoodDescription {
	private int food_description_idx;
	private Food food;
	private Description description;
}
