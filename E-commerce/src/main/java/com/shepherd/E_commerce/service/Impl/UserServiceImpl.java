package com.shepherd.E_commerce.service.Impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.shepherd.E_commerce.dto.requests.CreateUserRequest;
import com.shepherd.E_commerce.dto.requests.UpdateUserRequest;
import com.shepherd.E_commerce.dto.response.GetUserByIdResponse;
import com.shepherd.E_commerce.dto.response.UserListResponse;
import com.shepherd.E_commerce.dto.response.UserResponse;
import com.shepherd.E_commerce.dto.response.UserUpdateResponse;
import com.shepherd.E_commerce.exceptions.EmailAlreadyExistsException;
import com.shepherd.E_commerce.exceptions.PasswordMismatchException;
import com.shepherd.E_commerce.exceptions.UserNotFoundException;
import com.shepherd.E_commerce.mappers.UserMapper;

import com.shepherd.E_commerce.models.User;
import com.shepherd.E_commerce.repository.UserRepository;
import com.shepherd.E_commerce.service.CartService;
import com.shepherd.E_commerce.service.UserService;

import jakarta.persistence.EntityNotFoundException;


@Service
public class UserServiceImpl implements UserService{
	
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final BCryptPasswordEncoder passwordEncoder;
	private final CartService cartService;
	
	public UserServiceImpl (UserRepository userRepository,
			UserMapper userMapper,
			BCryptPasswordEncoder passwordEncoder,
			@Lazy CartService cartService) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
		this.cartService = cartService;
	}
	
	@Override
	public User loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
		return user;
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
				.password(passwordEncoder.encode(request.password()))
				.authorities(request.authorities())
				.accountNonExpired(true)
				.credentialsNonExpired(true)
				.isEnabled(true)
				.accountNonLocked(true)
				.build();
		User persistUser = userRepository.save(user);
		cartService.newCart(persistUser);
		return persistUser;
		
		
	}

	@Override
	public List<UserListResponse> getAllUsers() {
		List<User> users = userRepository.findAll();
		List<UserListResponse> userReponseList = users.stream().map(user -> userMapper.UserEntityToResponse(user))
				.collect(Collectors.toList());
		return userReponseList;
	}



	@Override
	public void deleteUserById(UUID user_id) {
		if(!userRepository.existsById(user_id)) {
			throw new UserNotFoundException("the user is not found!");
		}
		userRepository.deleteById(user_id);
	}



	@Override
	public UserUpdateResponse updateUserById(UUID user_id, UpdateUserRequest updateUserRequest) {
		if(!userRepository.existsById(user_id)) {
			throw new UserNotFoundException("The user is not found, so you cannot perform update process");
		}
		User user = userRepository.getReferenceById(user_id);
		
		user.setUsername(updateUserRequest.username());
		user.setEmail(updateUserRequest.email());
		
		//?????????????? spagetti!!!!!!
		if(updateUserRequest.old_password().equals(user.getPassword()) && updateUserRequest.new_password() != null && 
				updateUserRequest.confirm_new_password() != null) {
			if(!updateUserRequest.new_password().equals(updateUserRequest.confirm_new_password())) {
				throw new PasswordMismatchException("Passwords do not match");
			}
			
			user.setPassword(updateUserRequest.new_password());
		}
		
		userRepository.save(user);
		
		return userMapper.UserEntityToUpdateResponse(user);
	}



	@Override
	public GetUserByIdResponse getUserById(UUID id) {
		if(!userRepository.existsById(id)) {
			throw new UserNotFoundException("The user is not found");
		}
		
		User user = userRepository.getReferenceById(id);
		
		GetUserByIdResponse response = userMapper.UserEntityToResponseById(user);
		return response;
	}

	@Override
	public UserResponse getByEmail(String email) {
		
		if(!userRepository.existsByEmail(email)) {
			throw new UserNotFoundException("The user is not found");
		}
		User user = userRepository.getReferenceByEmail(email);
		
		UserResponse response = userMapper.UserEntityToResponseByEmail(user);
		return response;
	}

	@Override
	public User getUserByIdAsEntity(UUID user_id) {
		if(!userRepository.existsById(user_id)) {
			throw new UsernameNotFoundException("The user is not found!!");
		}
		
		User user = userRepository.getReferenceById(user_id);
		return user;
	}

	@Override
	public User getUserByEmailAsEntity(String email) {
		// TODO Auto-generated method stub
		return userRepository.getReferenceByEmail(email);
	}
	
	



	


	
	
	

}
