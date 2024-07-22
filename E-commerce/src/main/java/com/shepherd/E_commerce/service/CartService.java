package com.shepherd.E_commerce.service;



import com.shepherd.E_commerce.dto.requests.CartItem;
import com.shepherd.E_commerce.models.Cart;
import com.shepherd.E_commerce.models.User;

public interface CartService {
	
	
	void addItemToCart(CartItem request, String token);
	Cart newCart(User user);

	

}
