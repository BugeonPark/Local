package com.sds.weatherapp.model.local;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.weatherapp.domain.Member;
import com.sds.weatherapp.domain.Story;

@Mapper
public interface StoryDAO {
	public int insert(Story story);
	public List<Story> selectByMember(String dong);
	public List<Story> selectByXY(Story story);
	public List<Story> selectByPlaceName(String place_name);
}
