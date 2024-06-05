package com.shepherd.E_commerce.service;

import java.util.List;
import java.util.UUID;

import com.shepherd.E_commerce.dto.requests.CreateUserRequest;
import com.shepherd.E_commerce.dto.requests.UpdateUserRequest;
import com.shepherd.E_commerce.dto.response.UserListResponse;
import com.shepherd.E_commerce.dto.response.UserUpdateResponse;
import com.shepherd.E_commerce.models.User;

public interface UserService {
	
	public User createUser(CreateUserRequest request);
	public List<UserListResponse> getAllUsers();
	public void deleteUserById(UUID user_id);
	public UserUpdateResponse updateUserById(UUID user_id,UpdateUserRequest updateUserRequest);

}
