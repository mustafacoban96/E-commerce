package com.shepherd.E_commerce.service;



import com.shepherd.E_commerce.dto.requests.CartItem;
import com.shepherd.E_commerce.dto.response.IndividualCartItemResponse;
import com.shepherd.E_commerce.models.Cart;
import com.shepherd.E_commerce.models.Products;
import com.shepherd.E_commerce.models.User;

import java.util.List;
import java.util.UUID;

public interface CartService {
	
	
	void addItemToCart(CartItem request, String token);
	Cart newCart(User user);
	public List<IndividualCartItemResponse> getAllCartItemByUserId(String token);

	public void removeItemFromCart(CartItem request, String token);

}
