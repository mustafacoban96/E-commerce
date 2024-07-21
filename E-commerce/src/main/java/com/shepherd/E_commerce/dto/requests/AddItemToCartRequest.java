package com.shepherd.E_commerce.dto.requests;

import java.util.Set;

import com.shepherd.E_commerce.models.Products;
import com.shepherd.E_commerce.models.User;

import jakarta.validation.constraints.NotNull;
import lombok.Data;



@Data
public class AddItemToCartRequest {
	
	@NotNull(message = "Product cannot be null")
	private Set<Products> cart_items;

	
	
}


