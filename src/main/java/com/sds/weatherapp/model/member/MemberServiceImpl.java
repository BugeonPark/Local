package com.sds.weatherapp.model.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sds.weatherapp.domain.Member;
import com.sds.weatherapp.domain.MemberDetail;
import com.sds.weatherapp.domain.MemberUpdate;
import com.sds.weatherapp.domain.Role;
import com.sds.weatherapp.domain.Sns;
import com.sds.weatherapp.exception.MemberException;

@Service
public class MemberServiceImpl implements MemberService{
	@Autowired
	private MemberDAO memberDAO;
	@Autowired
	private MemberDetailDAO memberDetailDAO;
	@Autowired
	private SnsDAO snsDAO;
	@Autowired
	private RoleDAO roleDAO;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private MemberUpdateDAO memberUpdateDAO;
	
	@Override
	@Transactional
	public void regist(Member member) throws MemberException{
		
		Sns sns = snsDAO.selectByName(member.getSns().getSns_name());
		member.setSns(sns);
		
		Role role = roleDAO.selectByName(member.getRole().getRole_name());
		member.setRole(role);
		
		int result = memberDAO.insert(member);
		if(result < 1) {
			throw new MemberException("회원가입 실패");
		}
		if(sns.getSns_name().equals("homepage")) {
			MemberDetail memberDetail = member.getMemberDetail();
			memberDetail.setMember(member);
			//비밀번호 암호화 처리해야 함
			memberDetail.setPassword(passwordEncoder.encode(memberDetail.getPassword()));
			result = memberDetailDAO.insert(memberDetail);
			if(result<1) {
				throw new MemberException("회원 상세정보 등록 실패");
			}
		}
	}
	
	@Override
	public Member login(Member member) throws MemberException{
		Member dto = memberDAO.login(member);
		if(dto==null) {
			throw new MemberException("회원 정보가 일치하지 않습니다");
		}
		return dto;
	}
	
	@Override
	public Member selectByUid(String uid) {
		return memberDAO.selectByUid(uid);
	}
	
	@Override
	public Member update(Member member, MemberUpdate memberUpdate) throws MemberException{
		int result = 0;
		if(member.getSns().getSns_name()=="homepage") {
			result = memberUpdateDAO.updateHomepage(memberUpdate);
		}else {
			if(member.getMemberDetail()==null) {
				result = memberUpdateDAO.insertIfNull(memberUpdate);
				if(result<1) {
					throw new MemberException("회원 상세정보 초기 등록 업데이트 실패");
				}
			}else {
				result = memberUpdateDAO.updateSns(memberUpdate);
				if(result<1) {
					throw new MemberException("sns가입자 상세정보 업데이트 실패");
				}
			}
		}
		Member dto = memberDAO.selectByUid(member.getUid());
		return dto;
	}
}
