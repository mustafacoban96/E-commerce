package com.shepherd.E_commerce.controllers;

import com.shepherd.E_commerce.dto.response.OrderItemsByOrderIdResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shepherd.E_commerce.service.OrderItemsService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/order-items")
public class OrderItemsController {
	
	private final OrderItemsService orderItemsService;
	
	public OrderItemsController(OrderItemsService orderItemsService) {
		this.orderItemsService = orderItemsService;
	}


	@GetMapping("/{order_id}")
	public ResponseEntity<List<OrderItemsByOrderIdResponse>> getOrderItemsByOrderId(@PathVariable("order_id") UUID order_id){
		List<OrderItemsByOrderIdResponse> response = orderItemsService.getOrderItemsByOrderId(order_id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}




	
	
	
}
