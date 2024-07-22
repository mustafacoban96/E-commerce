package com.shepherd.E_commerce.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class IndividualCartItemResponse {

    private UUID id;
    private String name;
    private String description;
    private Integer stock;
    private Long price;
}
