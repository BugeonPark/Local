package com.sds.weatherapp.model.local;

import java.util.List;

import com.sds.weatherapp.domain.Member;
import com.sds.weatherapp.domain.Story;

public interface StoryService {
	public void regist(Story story);
	public List<Story> getPlaces(Member member);
	public List<Story> getStories(String place_name);
}
