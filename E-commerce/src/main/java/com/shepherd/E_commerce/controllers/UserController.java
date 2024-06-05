package com.shepherd.E_commerce.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shepherd.E_commerce.dto.requests.CreateUserRequest;
import com.shepherd.E_commerce.dto.response.UserListResponse;
import com.shepherd.E_commerce.exceptions.EmailAlreadyExistsException;
import com.shepherd.E_commerce.exceptions.PasswordMismatchException;
import com.shepherd.E_commerce.models.User;
import com.shepherd.E_commerce.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	
	
	
	@PostMapping("/createUser")
	public ResponseEntity<String> createUser(@Valid @RequestBody CreateUserRequest request){
		userService.createUser(request); 
		return new ResponseEntity<>("User is created successfully", HttpStatus.CREATED);
	}
	
	@GetMapping("/getUsers")
	public ResponseEntity<List<UserListResponse>> listOfUsers(){
		List<UserListResponse> listUserResponse = userService.getAllUsers();
		return new ResponseEntity<>(listUserResponse,HttpStatus.OK);
	}

}
