package com.shepherd.E_commerce.service;


import com.shepherd.E_commerce.dto.requests.AddItemToCartRequest;
import com.shepherd.E_commerce.models.Cart;
import com.shepherd.E_commerce.models.User;

public interface CartService {
	
	
	void addItemToCart(AddItemToCartRequest request,String token);
	Cart newCart(User user);

	

}
