package com.shepherd.E_commerce.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shepherd.E_commerce.models.Orders;

public interface OrdersRepository extends	JpaRepository<Orders, UUID>{

    List<Orders> findByUserId(UUID userId);

}
