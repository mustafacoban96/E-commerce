package com.shepherd.E_commerce.dto.response;

import java.util.UUID;

public record GetUserByIdResponse(
		UUID id,
		String email,
		String username
		
		
		) {

}
