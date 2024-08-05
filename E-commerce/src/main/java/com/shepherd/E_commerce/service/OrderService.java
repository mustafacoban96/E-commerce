package com.shepherd.E_commerce.service;

import com.shepherd.E_commerce.dto.requests.OrderRequest;
import com.shepherd.E_commerce.models.Orders;

import java.util.UUID;


public interface OrderService {

    public Orders createOrder(OrderRequest request);
    public Orders getOrderById(UUID order_id);

}
