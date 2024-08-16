package com.shepherd.E_commerce.mappers;

import com.shepherd.E_commerce.dto.response.OrderItemsByOrderIdResponse;
import com.shepherd.E_commerce.models.OrderItems;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderItemsMapper {


    public OrderItemsByOrderIdResponse toOrderItemsByOrderIdResponse(OrderItems orderItems){
        return new OrderItemsByOrderIdResponse(
                orderItems.getName(),
                orderItems.getQuantity(),
                orderItems.getUnit_price(),
                orderItems.getTotal_price()
        );
    }
}
