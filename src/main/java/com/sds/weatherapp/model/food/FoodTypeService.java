package com.sds.weatherapp.model.food;

import java.util.List;

import com.sds.weatherapp.domain.FoodCategory;
import com.sds.weatherapp.domain.Taste;

public interface FoodTypeService {
	public List<Taste> getTaste();
	public List<FoodCategory> getCategory();
}
