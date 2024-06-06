package com.shepherd.E_commerce.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateProductRequest(
		

		@NotBlank(message = "product name is mandatory")
		String name,
		@Size(min = 1 , max = 100)
		String description,
		@NotNull(message = "product stock is mandatory")
		Integer stock,
		@NotNull(message = "product price is mandatory")
		Long price
		
		
		) {

}
