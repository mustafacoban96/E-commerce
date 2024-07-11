package com.shepherd.E_commerce.dto.response;

public record AuthenticationResponse(
		
		String access_token,
		UserResponse user_response,
		String refresh_token,
		String token_type
		
		) {
	
	public AuthenticationResponse(String access_token, UserResponse user_response,String refresh_token) {
		this(access_token, user_response, refresh_token,"Bearer ");
	}

}
