package com.shepherd.E_commerce.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shepherd.E_commerce.models.Orders;

public interface OrdersRepository extends	JpaRepository<Orders, UUID>{

}
