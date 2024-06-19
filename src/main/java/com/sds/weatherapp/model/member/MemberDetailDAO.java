package com.sds.weatherapp.model.member;

import org.apache.ibatis.annotations.Mapper;

import com.sds.weatherapp.domain.MemberDetail;

@Mapper
public interface MemberDetailDAO {
	public int insert(MemberDetail memberDetail);
	public MemberDetail selectByMemberIdx(int member_idx);
}
