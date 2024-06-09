package com.shepherd.E_commerce.service.securityService;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shepherd.E_commerce.exceptions.RefreshTokenNotFound;
import com.shepherd.E_commerce.models.RefreshToken;
import com.shepherd.E_commerce.models.User;
import com.shepherd.E_commerce.repository.RefreshTokenRepository;
import com.shepherd.E_commerce.service.RefreshTokenService;
import com.shepherd.E_commerce.service.UserService;


@Service
@Transactional
public class RefreshTokenImpl implements RefreshTokenService{
	
	@Value("${jwt.refresh.expirationMs}")
	private Long refreshTokenExpiration;
	
	
	private final RefreshTokenRepository refreshTokenRepository;
	private final UserService userService;
	
	public RefreshTokenImpl(RefreshTokenRepository refreshTokenRepository, UserService userService) {
		this.refreshTokenRepository = refreshTokenRepository;
		this.userService = userService;
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
		
		refreshToken.setUser(userService.getUserByEmailAsEntity(email));
		refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenExpiration));
		refreshToken.setToken(UUID.randomUUID().toString());
		
		return refreshTokenRepository.save(refreshToken);
	}

	@Override
	public RefreshToken verifyExpiration(RefreshToken token) {
		
		if(token.getExpiryDate().compareTo(Instant.now()) < 0) {
			refreshTokenRepository.delete(token);
		}
		
		return token;
	}

	@Override
	public int deleteByUserId(UUID user_id) {
		
		if(!refreshTokenRepository.existsByUserId(user_id)) {
			throw new RefreshTokenNotFound("Invalid token");
		}
		
		User user = userService.getUserByIdAsEntity(user_id);
		
		return refreshTokenRepository.deleteByUser(user);
	}

	@Override
	public Boolean existsByUserId(UUID user_id) {
		
		return refreshTokenRepository.existsByUserId(user_id);
	}

	

}
