package com.sds.weatherapp.model.food.recommend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.model.Preference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.weatherapp.domain.Food;
import com.sds.weatherapp.domain.FoodCategory;
import com.sds.weatherapp.domain.FoodTaste;
import com.sds.weatherapp.domain.Member;
import com.sds.weatherapp.domain.PreferCommentDoc;
import com.sds.weatherapp.model.food.FoodCategoryDAO;
import com.sds.weatherapp.model.food.FoodDAO;
import com.sds.weatherapp.model.food.comments.CommentsDocDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecommendServiceImpl implements RecommendService{

	@Autowired
	private CommentsDocDAO commentsDocDAO;
	@Autowired
	private FoodCategoryDAO foodCategoryDAO;
	@Autowired
	private FoodDAO foodDAO;
	
	private double minScore = 0.2;
	
	@Override
	public List getList(Member member, Map map) {
		
		List<PreferCommentDoc> commentsList = commentsDocDAO.selectAllByMember(member.getMember_idx());
		Map<Long, FoodCategory> metadataMap = new HashMap();
		
		List<Preference> preferences = new ArrayList();
		for(int i=0; i<commentsList.size(); i++) {
			PreferCommentDoc doc = commentsList.get(i);
			GenericPreference preference = new GenericPreference((long)doc.getMember_idx(), doc.getFood_category_idx(), doc.getScore());
			preferences.add(preference);
			FoodCategory foodCategory = foodCategoryDAO.selectByIdx(doc.getFood_category_idx()); 
			metadataMap.put((long)foodCategory.getFood_category_idx(), foodCategory);
		}
		
		//사용자의 선호 카테고리
		List<FoodCategory> likedCategory = new ArrayList();
		
		likedCategory = preferences.stream()
				.filter(p -> p.getValue() > minScore)
				.map(p -> metadataMap.get(p.getItemID()))
				.collect(Collectors.toList());
		
		//날씨에 어울리는 음식들
		List<Food> foodList = foodDAO.selectByWeather(map);
		
		Map<Long, Food> candiMap = new HashMap();
		foodList.stream().forEach(f -> candiMap.put((long)f.getFood_idx(), f));
		
		Food favorite = member.getMemberDetail().getFavoriteFood();
		log.debug("날씨에 맞는 음식 : " + foodList);
		log.debug("사용자 최애 음식 : " + favorite);
		log.debug("사용자 선호 카테고리 : " + likedCategory);
		
		Map<Long, Double> calMap = new HashMap();
		for(Food food : foodList) {
			double score = getScore(favorite, food, likedCategory);
			calMap.put((long)food.getFood_idx(), score);
		}
		
		List<Food> result = new ArrayList();
		result = calMap.entrySet().stream()
				.sorted(Map.Entry.<Long, Double> comparingByValue().reversed())
				.limit(3)
				.map(e -> foodDAO.selectByFoodIdx((int)(long)e.getKey()))
				.collect(Collectors.toList());
		
		return result;
	}

	public double getScore(Food f1, Food f2, List<FoodCategory> likedCategory) {
		double score = 0.0;
		
		FoodCategory f1Category = f1.getFoodCategory();
		FoodCategory f2Category = f2.getFoodCategory();
		
		if(f1Category.getFood_category_idx()==f2Category.getFood_category_idx())
			score += 3.0;
		
		if(likedCategory != null) {
			for(FoodCategory fc : likedCategory) {
				if(fc.getFood_category_idx()==f2Category.getFood_category_idx()) {
					score += 7.0;
				}
			}
		}
		
		List<FoodTaste> tastes1 = f1.getTastes();
		List<FoodTaste> tastes2 = f2.getTastes();
		for(FoodTaste ft1 : tastes1) {
			for(FoodTaste ft2 : tastes2) {
				if(ft1.getTaste().getTaste_idx() == ft2.getTaste().getTaste_idx()) {
					score += 0.7;
				}
			}
		}
		
		return score;
	}
}
