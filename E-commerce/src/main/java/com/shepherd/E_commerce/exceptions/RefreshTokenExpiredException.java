package com.shepherd.E_commerce.exceptions;

public class RefreshTokenExpiredException extends RuntimeException{
	
	
	
	public RefreshTokenExpiredException(String message) {
		super(message);
	}

}
