package com.sds.weatherapp.model.food.comments;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.weatherapp.domain.PreferCommentDoc;
import com.sds.weatherapp.domain.SentimentDic;
import com.sds.weatherapp.exception.CommentsException;
import com.sds.weatherapp.model.food.recommend.SentimentDicDAO;

import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.util.common.model.Pair;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommentsServiceImpl implements CommentsService{
	
	@Autowired
	private Komoran komoran;
	@Autowired
	private CommentsDocDAO commentsDocDAO;
	@Autowired
	private SentimentDicDAO sentimentDicDAO;
	
	@Override
	public void registComments(PreferCommentDoc preferCommentDoc) throws CommentsException{
		
		String txt = preferCommentDoc.getContent().replaceAll("[^a-zA-Z0-9가-힣\\s]", "");
		
		List<Pair<String, String>> resultList = komoran.analyze(txt).getList();
		float score = 0;
		for(Pair<String, String> pair : resultList) {
			
			SentimentDic dic = sentimentDicDAO.select(pair.getFirst()+"/" + pair.getSecond());
			if(dic != null) {
				log.debug(pair.getFirst() + " 사전 검색 긍정 점수 : " + dic.getPOS() + " 부정 점수 : " + dic.getNEG());
				score += dic.getPOS() - dic.getNEG();
			}else {
				log.debug("사전에 존재하지 않음");
			}
		}
		log.debug(preferCommentDoc.getFood_category_idx()+"번째 카테고리의 선호 점수 : " + score);
		preferCommentDoc.setScore(score);
		commentsDocDAO.insert(preferCommentDoc);
	}
}
