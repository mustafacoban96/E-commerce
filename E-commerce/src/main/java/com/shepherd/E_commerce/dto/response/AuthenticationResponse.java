package com.shepherd.E_commerce.dto.response;

public record AuthenticationResponse(
		
		String access_token,
		UserResponse userResponse,
		String type
		
		) {
	
	public AuthenticationResponse(String token, UserResponse userInfo) {
		this(token, userInfo, "Bearer ");
	}

}
