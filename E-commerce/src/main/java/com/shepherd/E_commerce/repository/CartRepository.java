package com.shepherd.E_commerce.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shepherd.E_commerce.models.Cart;
import com.shepherd.E_commerce.models.User;

public interface CartRepository extends JpaRepository<Cart, UUID>{

    Optional<Object> findByUserId(UUID user_id);
}
