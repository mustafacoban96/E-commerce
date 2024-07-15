package com.shepherd.E_commerce.controllers.auth;


import java.util.Optional;
import java.util.UUID;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shepherd.E_commerce.dto.requests.CreateUserRequest;
import com.shepherd.E_commerce.dto.requests.LoginRequest;
import com.shepherd.E_commerce.dto.requests.LogoutRequest;
import com.shepherd.E_commerce.dto.requests.TokenRefreshRequest;
import com.shepherd.E_commerce.dto.response.AuthenticationResponse;
import com.shepherd.E_commerce.dto.response.TokenRefreshResponse;
import com.shepherd.E_commerce.dto.response.UserResponse;
import com.shepherd.E_commerce.exceptions.RefreshTokenExpiredException;
import com.shepherd.E_commerce.exceptions.RefreshTokenNotFound;
import com.shepherd.E_commerce.models.RefreshToken;
import com.shepherd.E_commerce.service.RefreshTokenService;
import com.shepherd.E_commerce.service.UserService;
import com.shepherd.E_commerce.service.securityService.JwtService;
import com.shepherd.E_commerce.service.securityService.JwtTokenBlackListService;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
@CrossOrigin(origins = "http://localhost:3000/")
public class AuthController {

	private final UserService userService;
	private final JwtService jwtService;
	private AuthenticationManager authenticationManager;
	private final RefreshTokenService refreshTokenService;
	private final JwtTokenBlackListService jwtTokenBlackListService;
	
	
	public AuthController(
			UserService userService, 
			JwtService jwtService, 
			AuthenticationManager authenticationManager,
			RefreshTokenService refreshTokenService,
			JwtTokenBlackListService jwtTokenBlackListService
			) {
		
		this.userService = userService;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
		this.refreshTokenService = refreshTokenService;
		this.jwtTokenBlackListService = jwtTokenBlackListService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@Valid @RequestBody CreateUserRequest request){
		
		userService.createUser(request);
		
		return new ResponseEntity<>("You registered successfully", HttpStatus.CREATED);
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request){
		
		
		UUID user_id = userService.getByEmail(request.email()).user_id();
		if(refreshTokenService.existsByUserId(user_id)) {
			refreshTokenService.deleteByUserId(user_id);
		}
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
		if(authentication.isAuthenticated()) {
			String token = jwtService.generateToken(request.email());
			UserResponse response = userService.getByEmail(request.email());
			String refreshToken = refreshTokenService.createRefreshToken(request.email()).getToken();
			return new ResponseEntity<>(new AuthenticationResponse(token, response,refreshToken),HttpStatus.OK);
		}
		log.info("Invalid email "  + request.email());
		throw new UsernameNotFoundException("Invalid email {}" + request.email());
		
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> logout(@RequestHeader(value = "Authorization",required = false) String bearerToken,@RequestBody LogoutRequest request){
		
		String token = bearerToken.substring(7);
		jwtTokenBlackListService.blackListToken(token);
		Optional<RefreshToken> refreshToken =  refreshTokenService.findByToken(request.refresh_token());
		UUID id= refreshToken.get().getUser().getId();
		refreshTokenService.deleteByUserId(id);
		
		
		return new ResponseEntity<>("Log out successfully",HttpStatus.OK);
		
	}
	
	
	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest request){
		
		String requestRefreshToken = request.refresh_token();
		Optional<RefreshToken> refreshTokenOptional = refreshTokenService.findByToken(requestRefreshToken);
		RefreshToken refreshToken  = refreshTokenOptional.get();
		try {
			refreshTokenService.verifyExpiration(refreshToken);
			System.out.println("ttttt");
		} catch (RefreshTokenExpiredException e) {
			refreshTokenService.deleteByToken(requestRefreshToken);
			System.out.println("aaaaa");
			throw e;
		}	
			String email = refreshToken.getUser().getEmail();
		    String token = jwtService.generateToken(email);
		    System.out.println("eeeee");
		    return new ResponseEntity<>(new TokenRefreshResponse(token, requestRefreshToken), HttpStatus.OK);
		 
		/*return refreshTokenService.findByToken(requestRefreshToken)
				.map(refreshTokenService::verifyExpiration)
				.map(RefreshToken::getUser)
				.map(user ->{
					String token = jwtService.generteToken(user.getEmail());
					return new ResponseEntity<>(new TokenRefreshResponse(token, requestRefreshToken),HttpStatus.OK);
				}).orElseThrow(() -> new RefreshTokenNotFound("Invalid token"));*/
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
