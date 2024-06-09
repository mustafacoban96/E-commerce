package com.shepherd.E_commerce.exceptions;

public class RefreshTokenNotFound extends RuntimeException{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RefreshTokenNotFound(String message) {
		super(message);
	}

}
