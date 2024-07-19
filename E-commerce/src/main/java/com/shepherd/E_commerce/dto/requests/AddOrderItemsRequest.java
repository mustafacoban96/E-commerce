package com.shepherd.E_commerce.dto.requests;

import com.shepherd.E_commerce.models.Products;


import lombok.Data;


@Data
public class AddOrderItemsRequest {
	
	private Products product;
	private Integer quantity;
	private Integer unitPrice;

}
