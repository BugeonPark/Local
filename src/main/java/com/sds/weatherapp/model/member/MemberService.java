package com.sds.weatherapp.model.member;

import com.sds.weatherapp.domain.Member;
import com.sds.weatherapp.domain.MemberUpdate;

public interface MemberService {
	
	public void regist(Member member);
	public Member login(Member member);
	public Member selectByUid(String uid);
	public Member update(Member member, MemberUpdate memberUpdate);
}
