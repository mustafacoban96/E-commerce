package com.shepherd.E_commerce.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.shepherd.E_commerce.models.OrderItems;

public interface OrderItemsRepository extends JpaRepository<OrderItems, UUID>{

}
