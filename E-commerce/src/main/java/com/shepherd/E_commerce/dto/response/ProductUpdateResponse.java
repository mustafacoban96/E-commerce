package com.shepherd.E_commerce.dto.response;

import java.util.UUID;

public record ProductUpdateResponse(
		
		UUID id,
		String name,
		String description,
		Integer stock,
		Long price
		) {

}
