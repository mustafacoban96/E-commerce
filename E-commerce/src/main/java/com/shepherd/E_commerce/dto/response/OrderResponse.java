package com.shepherd.E_commerce.dto.response;

import java.util.UUID;

public record OrderResponse(
        UUID order_id,
        String delivery_by,
        Long order_total
) {
}
