package com.shepherd.E_commerce.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class OrderRequest {

    private Set<OrderItemRequest> order_items_list;
    @NotNull
    private UUID user_id;
    @NotNull
    private Long total_price;

}
