package com.shepherd.E_commerce.dto.response;


import java.util.Date;
import java.util.UUID;

public record CreateOrderResponse(
        UUID order_id,
        String delivery_by,
        Long order_total,
        Boolean is_success_order
) {

}
