package com.sds.weatherapp.model.image;

import java.io.IOException;

import com.sds.weatherapp.domain.Urls;

import lombok.extern.slf4j.Slf4j;

public interface ImgAPIService {
	public Urls getImage(String keyword);
}
