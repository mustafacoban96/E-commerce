package com.shepherd.E_commerce.service.Impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shepherd.E_commerce.dto.requests.CreateUserRequest;
import com.shepherd.E_commerce.dto.response.UserListResponse;
import com.shepherd.E_commerce.exceptions.EmailAlreadyExistsException;
import com.shepherd.E_commerce.exceptions.PasswordMismatchException;
import com.shepherd.E_commerce.mappers.UserMapper;
import com.shepherd.E_commerce.models.User;
import com.shepherd.E_commerce.repository.UserRepository;
import com.shepherd.E_commerce.service.UserService;


@Service
public class UserServiceImpl implements UserService{
	
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	
	public UserServiceImpl (UserRepository userRepository,UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}

	
	
	//create user
	@Override
	public User createUser(CreateUserRequest request) {
		
		if(!request.password().equals(request.confirm_password())) {
			throw new PasswordMismatchException("Passwords do not match");
		}
		
		if(userRepository.existsByEmail(request.email())) {
			throw new EmailAlreadyExistsException("Email is exist");
		}
		
		User user = User.builder()
				.username(request.username())
				.email(request.email())
				.password(request.password())
				.build();
		return userRepository.save(user);
		
		
	}



	@Override
	public List<UserListResponse> getAllUsers() {
		List<User> users = userRepository.findAll();
		List<UserListResponse> userReponseList = users.stream().map(user -> userMapper.UserEntityToResponse(user))
				.collect(Collectors.toList());
		
		return userReponseList;
	}
	
	

}
