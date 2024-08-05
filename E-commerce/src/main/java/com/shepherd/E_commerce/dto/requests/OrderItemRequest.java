package com.shepherd.E_commerce.dto.requests;


import lombok.Data;

import java.util.UUID;

@Data
public class OrderItemRequest {

    private UUID product_id;
    private String product_name;
    private Integer quantity;
    private Long unit_price;
    private Long total_price_per_product;
}
