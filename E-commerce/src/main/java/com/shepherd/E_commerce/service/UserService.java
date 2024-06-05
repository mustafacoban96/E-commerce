package com.shepherd.E_commerce.service;

import java.util.List;

import com.shepherd.E_commerce.dto.requests.CreateUserRequest;
import com.shepherd.E_commerce.dto.response.UserListResponse;
import com.shepherd.E_commerce.models.User;

public interface UserService {
	
	public User createUser(CreateUserRequest request);
	public List<UserListResponse> getAllUsers();

}
