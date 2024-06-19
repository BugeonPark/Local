package com.sds.weatherapp.model.member;

import org.apache.ibatis.annotations.Mapper;

import com.sds.weatherapp.domain.Member;

@Mapper
public interface MemberDAO {
	public int insert(Member member);
	public Member login(Member member);
	public Member selectByUid(String uid);
	public Member selectByMemberIdx(int member_idx);
}
