package com.sds.weatherapp.model.member;

import org.apache.ibatis.annotations.Mapper;

import com.sds.weatherapp.domain.MemberUpdate;

@Mapper
public interface MemberUpdateDAO {
	public int insertIfNull(MemberUpdate memberUpdate);
	public int updateSns(MemberUpdate memberUpdate);
	public int updateHomepage(MemberUpdate memberUpdate);
}
