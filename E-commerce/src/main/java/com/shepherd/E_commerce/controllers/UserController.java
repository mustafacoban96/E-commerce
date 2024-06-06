package com.shepherd.E_commerce.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shepherd.E_commerce.dto.requests.CreateUserRequest;
import com.shepherd.E_commerce.dto.requests.UpdateUserRequest;
import com.shepherd.E_commerce.dto.response.GetUserByIdResponse;
import com.shepherd.E_commerce.dto.response.UserListResponse;
import com.shepherd.E_commerce.dto.response.UserUpdateResponse;
import com.shepherd.E_commerce.service.UserService;
import jakarta.validation.Valid;

/*
 * https://medium.com/deliveryherotechhub/rest-api-tasar%C4%B1m%C4%B1-ve-best-practices-37ea7041b27
 * */

@RestController
@RequestMapping("/users")
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	
	
	
	@PostMapping("/create-user")
	public ResponseEntity<String> createUser(@Valid @RequestBody CreateUserRequest request){
		userService.createUser(request); 
		return new ResponseEntity<>("User is created successfully", HttpStatus.CREATED);
	}
	
	@GetMapping("/user/{user_id}")
	public ResponseEntity<GetUserByIdResponse> getUserById(@PathVariable("user_id") UUID id){
		
		GetUserByIdResponse response = userService.getUserById(id);
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping("/get-users")
	public ResponseEntity<List<UserListResponse>> listOfUsers(){
		List<UserListResponse> listUserResponse = userService.getAllUsers();
		return new ResponseEntity<>(listUserResponse,HttpStatus.OK);
	}
	
	@DeleteMapping("/delete-user/{user_id}")
	public ResponseEntity<String> deleteUserById(@PathVariable("user_id") UUID id){
		userService.deleteUserById(id);
		return new ResponseEntity<>("user is deleted successfully",HttpStatus.OK);
	}
	
	@PutMapping("/update-user/{user_id}")
	public ResponseEntity<UserUpdateResponse> updateByUserId(@PathVariable("user_id") UUID user_id,
			@Valid @RequestBody UpdateUserRequest updateUserRequest){
		
		UserUpdateResponse response =  userService.updateUserById(user_id, updateUserRequest);
		return new ResponseEntity<>(response,HttpStatus.OK);
		
	}
	
	

}
