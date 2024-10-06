package com.shepherd.E_commerce.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(
		
		
		@NotBlank(message = "Username is mandatory")
		String username,
		@Email
		@NotBlank(message="email is mandatory")
		String email,
		String new_password,
		String confirm_new_password
		
		) {

}
