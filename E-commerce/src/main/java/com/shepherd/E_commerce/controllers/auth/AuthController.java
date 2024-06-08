package com.shepherd.E_commerce.controllers.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shepherd.E_commerce.dto.requests.CreateUserRequest;
import com.shepherd.E_commerce.dto.requests.LoginRequest;
import com.shepherd.E_commerce.dto.response.AuthenticationResponse;
import com.shepherd.E_commerce.dto.response.UserResponse;
import com.shepherd.E_commerce.service.UserService;
import com.shepherd.E_commerce.service.securityService.JwtService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

	private final UserService userService;
	private final JwtService jwtService;
	private AuthenticationManager authenticationManager;
	
	
	public AuthController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
		
		this.userService = userService;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@Valid @RequestBody CreateUserRequest request){
		
		userService.createUser(request);
		
		return new ResponseEntity<>("You registered successfully", HttpStatus.CREATED);
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request){
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
		if(authentication.isAuthenticated()) {
			String token = jwtService.generteToken(request.email());
			UserResponse response = userService.getByEmail(request.email());
			return new ResponseEntity<>(new AuthenticationResponse(token, response),HttpStatus.OK);
		}
		log.info("Invalid email "  + request.email());
		throw new UsernameNotFoundException("Invalid email {}" + request.email());
		
	}
}
