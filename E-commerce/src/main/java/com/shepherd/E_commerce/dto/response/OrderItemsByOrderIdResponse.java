package com.shepherd.E_commerce.dto.response;

public record OrderItemsByOrderIdResponse(
        // id can be added
        String name,
        Integer quantity,
        Long unit_price,
        Long total_price
) {
}
