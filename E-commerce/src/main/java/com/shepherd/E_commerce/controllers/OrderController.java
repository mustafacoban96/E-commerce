package com.shepherd.E_commerce.controllers;

import com.shepherd.E_commerce.dto.requests.OrderRequest;
import com.shepherd.E_commerce.dto.response.CreateOrderResponse;
import com.shepherd.E_commerce.models.Orders;
import com.shepherd.E_commerce.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create-order")
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody OrderRequest request){
        CreateOrderResponse response = orderService.createOrder(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
