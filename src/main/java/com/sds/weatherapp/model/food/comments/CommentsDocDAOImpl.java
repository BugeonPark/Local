package com.sds.weatherapp.model.food.comments;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.sds.weatherapp.domain.PreferCommentDoc;
import com.sds.weatherapp.exception.CommentsException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class CommentsDocDAOImpl implements CommentsDocDAO{

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public void insert(PreferCommentDoc preferCommentDoc) throws CommentsException{
		try {
			mongoTemplate.insert(preferCommentDoc);
			log.debug("몽고 등록 성공");
		}catch(Exception e) {
			log.debug("설문 입력 실패");
			e.printStackTrace();
			throw new CommentsException("설문 입력 실패", e);
			
		}
		
	}

	@Override
	public List selectAllByMember(int member_idx) {
		Query query = new Query();
		query.addCriteria(Criteria.where("member_idx").is(member_idx));
		return mongoTemplate.find(query, PreferCommentDoc.class);
	}
	
	

}
