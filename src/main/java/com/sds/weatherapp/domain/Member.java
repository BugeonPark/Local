package com.sds.weatherapp.domain;

import lombok.Data;

@Data
public class Member {
	private int member_idx;
	private String uid;
	private String nickname;
	private MemberDetail memberDetail;
	private Role role;
	private Sns sns;
}
