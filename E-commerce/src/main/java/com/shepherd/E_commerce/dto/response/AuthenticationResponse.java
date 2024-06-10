package com.shepherd.E_commerce.dto.response;

public record AuthenticationResponse(
		
		String access_token,
		UserResponse userResponse,
		String refreshToken,
		String token_type
		
		) {
	
	public AuthenticationResponse(String token, UserResponse userInfo,String refreshToken) {
		this(token, userInfo, refreshToken,"Bearer ");
	}

}
