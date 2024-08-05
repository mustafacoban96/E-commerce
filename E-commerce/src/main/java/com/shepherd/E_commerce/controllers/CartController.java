package com.shepherd.E_commerce.controllers;

import java.util.List;
import java.util.UUID;

import com.shepherd.E_commerce.dto.requests.CartItem;
import com.shepherd.E_commerce.dto.response.IndividualCartItemResponse;
import com.shepherd.E_commerce.service.securityService.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.shepherd.E_commerce.service.CartService;

@RestController
@RequestMapping("/api/v1/cart")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {
	
	
	private final CartService cartService;

	
	public CartController(CartService cartService,JwtService jwtService) {
		this.cartService = cartService;
	}
	@PostMapping("/add-to-cart")
	public ResponseEntity<String> addItemToCart(@RequestHeader(value = "Authorization",required = false) String bearerToken,
												@RequestBody CartItem request){
		String token = bearerToken.substring(7);
		cartService.addItemToCart(request,token);
		return new ResponseEntity<>("Item was added to the cart successfully",HttpStatus.OK);
		}

	@GetMapping("/show-cart")
	public ResponseEntity<List<IndividualCartItemResponse>> showCartItemList(@RequestHeader(value = "Authorization",required = false) String bearerToken){
		String token = bearerToken.substring(7);
		List<IndividualCartItemResponse> cartlist = cartService.getAllCartItemByUserId(token);
		return new ResponseEntity<>(cartlist,HttpStatus.OK);
	}

	@PostMapping("/remove-from-cart")
	public ResponseEntity<String> removeItemFromTheCart(@RequestHeader(value = "Authorization",required = false) String bearerToken,
														@RequestBody CartItem request){
		String token = bearerToken.substring(7);
		cartService.removeItemFromCart(request,token);
		return new ResponseEntity<>("Item was removed to the cart succefully",HttpStatus.OK);

	}

}
