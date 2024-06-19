package com.sds.weatherapp.model.food.comments;

import java.util.List;

import com.sds.weatherapp.domain.PreferCommentDoc;

public interface CommentsDocDAO {
	public void insert(PreferCommentDoc preferCommentDoc);
	public List selectAllByMember(int member_idx);
}
