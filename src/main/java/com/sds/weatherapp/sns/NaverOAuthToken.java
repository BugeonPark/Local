package com.sds.weatherapp.sns;

import lombok.Data;

@Data
public class NaverOAuthToken {
	private String access_token;
	private String refresh_token;
	private String token_type;
	private String expires_in;
}