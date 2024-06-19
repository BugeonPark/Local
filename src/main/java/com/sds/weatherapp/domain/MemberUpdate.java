package com.sds.weatherapp.domain;

import lombok.Data;

@Data
public class MemberUpdate {
	private int member_idx;
	private String nickname;
	private String roadAddress;
	private String sido;
	private String sidoEnglish;
	private Food favoriteFood;
	private Food hateFood;
}
