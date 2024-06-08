package com.shepherd.E_commerce.dto.response;

import java.util.Set;
import java.util.UUID;

import com.shepherd.E_commerce.models.Roles;

public record UserResponse(
		
		UUID user_id,
		String username,
		String email,
		Set<Roles> roles
		
		
		) {

}
