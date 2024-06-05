package com.shepherd.E_commerce.exceptions;

public class UserNotFoundException extends UserException{
	
	private static final long serialVersionUID = 3L;

	 public UserNotFoundException(String message) {
		super(message);
	}

}
