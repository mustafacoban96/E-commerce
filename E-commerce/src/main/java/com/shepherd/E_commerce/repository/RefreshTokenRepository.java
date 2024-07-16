package com.shepherd.E_commerce.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.shepherd.E_commerce.models.RefreshToken;
import com.shepherd.E_commerce.models.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer>{
	
	Optional<RefreshToken> findByToken(String Token);
	
	@Modifying
	int deleteByUser(User user);
	Boolean existsByUserId(UUID user_id);
	Boolean existsByToken(String token);
	void deleteByUserId(UUID user_id);
}
