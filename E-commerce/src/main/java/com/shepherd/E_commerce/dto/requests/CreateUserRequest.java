package com.shepherd.E_commerce.dto.requests;

import java.util.Set;

import com.shepherd.E_commerce.models.Roles;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record CreateUserRequest(
		
		
		@NotBlank(message = "username cannot be empty and cannot contain space")
		@Pattern(regexp = "^[a-zA-Z0-9.\\-\\/+=@_]*$",message = "Invalid username")
		String username,
		@Email
		String email,
		@NotBlank(message = "password cannot be empty and cannot contain space")
		String password,
		@NotBlank(message = "confirming password is mandatory")
		@NotNull(message = "Invalid confirming")
		String confirm_password,
		Set<Roles> authorities
		
		
		
		) {
	

}
