package com.shepherd.E_commerce.service;


import com.shepherd.E_commerce.dto.requests.OrderItemRequest;
import com.shepherd.E_commerce.models.OrderItems;

import java.util.UUID;

public interface OrderItemsService {

    public OrderItems createOrderItem(OrderItemRequest request, UUID order_id);



}
