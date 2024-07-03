package com.shepherd.E_commerce.service;

import java.util.Optional;
import java.util.UUID;

import com.shepherd.E_commerce.models.RefreshToken;

public interface RefreshTokenService {
	
	
	public Optional<RefreshToken> findByToken(String token);
	public RefreshToken createRefreshToken(String email);
	public RefreshToken verifyExpiration(RefreshToken token);
	public int deleteByUserId(UUID user_id);
	public Boolean existsByUserId(UUID user_id);
	public void deleteByToken(String token);
	

}
