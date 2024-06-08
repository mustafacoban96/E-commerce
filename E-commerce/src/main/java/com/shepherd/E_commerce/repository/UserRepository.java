package com.shepherd.E_commerce.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shepherd.E_commerce.models.User;

public interface UserRepository extends JpaRepository<User, UUID>{
	Boolean existsByEmail(String email);
	Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);
	User getReferenceByEmail(String email);
	
}
