package com.shepherd.E_commerce.exceptions;

public class PasswordMismatchException extends UserException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	public PasswordMismatchException(String message) {
		super(message);
	}

}
