package com.sds.weatherapp.model.map;

import com.sds.weatherapp.domain.AddrDong;
import com.sds.weatherapp.domain.MemberDetail;
import com.sds.weatherapp.domain.Restaurant;
import com.sds.weatherapp.domain.Story;
import com.sds.weatherapp.domain.UserLocation;

public interface MapAPIService {
	
	public Restaurant getResaurant(String x, String y, String food_name);
	public UserLocation getUserLocation(MemberDetail memberDetail);
	public AddrDong getDong(Story story);
}
