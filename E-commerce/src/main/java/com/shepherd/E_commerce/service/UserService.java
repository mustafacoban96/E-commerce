package com.shepherd.E_commerce.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.shepherd.E_commerce.dto.requests.CreateUserRequest;
import com.shepherd.E_commerce.dto.requests.UpdateUserRequest;
import com.shepherd.E_commerce.dto.response.GetUserByIdResponse;
import com.shepherd.E_commerce.dto.response.UserListResponse;
import com.shepherd.E_commerce.dto.response.UserResponse;
import com.shepherd.E_commerce.dto.response.UserUpdateResponse;
import com.shepherd.E_commerce.models.User;

public interface UserService extends UserDetailsService{
	
	public User createUser(CreateUserRequest request);
	public List<UserListResponse> getAllUsers();
	public void deleteUserById(UUID user_id);
	public UserUpdateResponse updateUserById(UUID user_id,UpdateUserRequest updateUserRequest);
	public GetUserByIdResponse getUserById(UUID id);
	public UserResponse getByEmail(String email);
	public User getUserByIdAsEntity(UUID user_id);
	public User getUserByEmailAsEntity(String email);
	

}
