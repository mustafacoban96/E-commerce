package com.shepherd.E_commerce.controllers;

import java.util.UUID;

import com.shepherd.E_commerce.dto.requests.CartItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shepherd.E_commerce.dto.requests.AddItemToCartRequest;
import com.shepherd.E_commerce.service.CartService;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
	
	
	private final CartService cartService;
	
	
	
	public CartController(CartService cartService) {
		this.cartService = cartService;
		
		
	}
	
	
	@PostMapping("/add-to-cart")
	public ResponseEntity<String> addItemToCart(@RequestHeader(value = "Authorization",required = false) String bearerToken, @RequestBody CartItem request){
		String token = bearerToken.substring(7);
		cartService.addItemToCart(request,token);
		return new ResponseEntity<>("Item was added to the cart succefully",HttpStatus.OK);
		}

}
