package com.shepherd.E_commerce.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shepherd.E_commerce.models.User;

public interface UserRepository extends JpaRepository<User, UUID>{

}
