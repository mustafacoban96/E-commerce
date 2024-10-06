package com.shepherd.E_commerce.mappers;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.shepherd.E_commerce.dto.response.GetUserByIdResponse;
import com.shepherd.E_commerce.dto.response.UserListResponse;
import com.shepherd.E_commerce.dto.response.UserResponse;
import com.shepherd.E_commerce.dto.response.UserUpdateResponse;
import com.shepherd.E_commerce.models.User;

@Component
public class UserMapper {

	//list
	public UserListResponse UserEntityToResponse(User user) {
		if(user == null) {
			return null;
		}
		
		return new UserListResponse(user.getId(),user.getUsername(), user.getEmail());
	}
	
	//update
	public UserUpdateResponse UserEntityToUpdateResponse(User user) {
		if(user == null) {
			return null;
		}
		
		return new UserUpdateResponse(user.getId(), user.getEmail(),user.getUsername());
	}
	
	//individual user mapper
	public GetUserByIdResponse UserEntityToResponseById(User user) {
		if(user == null) {
			return null;
		}
		
		return new GetUserByIdResponse(user.getId(),user.getUsername(), user.getEmail());
	}
	
	
	
	//Get user by email for authentication
	public UserResponse UserEntityToResponseByEmail(User user) {
		if(user == null) {
			return null;
		}
		
		
		return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getAuthorities());
	}
	
	
	
	
	
}
