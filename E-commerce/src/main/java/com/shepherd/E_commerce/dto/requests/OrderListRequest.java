package com.shepherd.E_commerce.dto.requests;

import lombok.Data;

import java.util.UUID;


@Data
public class OrderListRequest {
    private UUID user_id;
}
