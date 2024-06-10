package com.shepherd.E_commerce.dto.response;

public record TokenRefreshResponse(
		
		String access_token,
		String refresh_token,
		String token_type
		
		
		) {
	
	public TokenRefreshResponse(String access_token, String refresh_token) {
		this(access_token,refresh_token,"Bearer ");
	}

}
