package com.shepherd.E_commerce.service.securityService;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shepherd.E_commerce.exceptions.RefreshTokenExpiredException;
import com.shepherd.E_commerce.exceptions.RefreshTokenNotFound;
import com.shepherd.E_commerce.models.RefreshToken;
import com.shepherd.E_commerce.models.User;
import com.shepherd.E_commerce.repository.RefreshTokenRepository;
import com.shepherd.E_commerce.repository.UserRepository;
import com.shepherd.E_commerce.service.RefreshTokenService;
import com.shepherd.E_commerce.service.UserService;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
@Transactional
@Slf4j
public class RefreshTokenImpl implements RefreshTokenService{
	
	@Value("${jwt.refresh.expirationMs}")
	private Long refreshTokenExpiration;
	
	
	private final RefreshTokenRepository refreshTokenRepository;
	//private final UserService userService;
	private final UserRepository userRepository;
	
	public RefreshTokenImpl(RefreshTokenRepository refreshTokenRepository,UserRepository userRepository) {
		this.refreshTokenRepository = refreshTokenRepository;
		//this.userService = userService;
		this.userRepository = userRepository;
	}

	@Override
	public Optional<RefreshToken> findByToken(String token) {
		
		if(!refreshTokenRepository.existsByToken(token)) {
			throw new RefreshTokenNotFound("Invalid token");
		}
		
		return refreshTokenRepository.findByToken(token);
	}

	@Override
	public RefreshToken createRefreshToken(String email) {
		RefreshToken refreshToken = new RefreshToken();
		
		//refreshToken.setUser(userService.getUserByEmailAsEntity(email));
		refreshToken.setUser(userRepository.getReferenceByEmail(email));
		refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenExpiration));
		refreshToken.setToken(UUID.randomUUID().toString());
		
		return refreshTokenRepository.save(refreshToken);
	}

	@Override
	public RefreshToken verifyExpiration(RefreshToken token) {
	    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
	        //log.info("Deleting expired token for user: " + token.getUser().getId());
	        //refreshTokenRepository.delete(token);
	        //refreshTokenRepository.flush();
	        //log.info("Expired token deleted");
	        throw new RefreshTokenExpiredException("Refresh token is expired. Please make a new login..!");
	    }
	    return token;
	}

	@Override
	public int deleteByUserId(UUID user_id) {
		
		if(!refreshTokenRepository.existsByUserId(user_id)) {
			throw new RefreshTokenNotFound("Invalid token");
		}
		User user = userRepository.getReferenceById(user_id);
		//User user = userService.getUserByIdAsEntity(user_id);
		
		return refreshTokenRepository.deleteByUser(user);
	}

	@Override
	public Boolean existsByUserId(UUID user_id) {
		
		return refreshTokenRepository.existsByUserId(user_id);
	}

	@Override
	public void deleteByToken(String token) {
		refreshTokenRepository.findByToken(token).ifPresent(refreshTokenRepository::delete);
		
	}

	

}
