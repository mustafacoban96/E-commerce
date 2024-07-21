package com.shepherd.E_commerce.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shepherd.E_commerce.service.OrderItemsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/orderItems")
public class OrderItemsController {
	
	private final OrderItemsService orderItemsService;
	
	public OrderItemsController(OrderItemsService orderItemsService) {
		this.orderItemsService = orderItemsService;
	}
	
	
	
}
