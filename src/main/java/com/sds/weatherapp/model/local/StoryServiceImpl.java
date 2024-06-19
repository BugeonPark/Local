package com.sds.weatherapp.model.local;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sds.weatherapp.common.FileManager;
import com.sds.weatherapp.domain.Member;
import com.sds.weatherapp.domain.Story;
import com.sds.weatherapp.exception.StoryException;

@Service
public class StoryServiceImpl implements StoryService{
	
	@Autowired
	private FileManager fileManager;
	@Autowired
	private StoryDAO storyDAO;
	
	@Transactional
	public void regist(Story story) throws StoryException{
		int result = 0;
		fileManager.save(story);
		result = storyDAO.insert(story);
		if(result<1) {
			throw new StoryException("스토리 등록 실패");
		}
	}
	
	public List<Story> getPlaces(Member member){
		String dong = member.getMemberDetail().getDong();
		List<Story> list = storyDAO.selectByMember(dong);
		return list;
	}

	@Override
	public List<Story> getStories(String place_name) {
		List<Story> list = storyDAO.selectByPlaceName(place_name);
		System.out.println("서비스에서 : " + list);
		return list;
	}
}
