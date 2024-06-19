package com.sds.weatherapp.model.food.recommend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.sds.weatherapp.domain.SentimentDic;

@Repository
public class SentimentDicDAOImpl implements SentimentDicDAO{
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public SentimentDic select(String word) {
		Query query = new Query(Criteria.where("ngram").is(word)); 
		return mongoTemplate.findOne(query, SentimentDic.class);
	}
}
