package com.shepherd.E_commerce.mappers;

import org.springframework.stereotype.Component;

import com.shepherd.E_commerce.dto.response.UserListResponse;
import com.shepherd.E_commerce.models.User;

@Component
public class UserMapper {

	
	public UserListResponse UserEntityToResponse(User user) {
		if(user == null) {
			return null;
		}
		
		return new UserListResponse(user.getUsername(), user.getEmail());
	}
}
