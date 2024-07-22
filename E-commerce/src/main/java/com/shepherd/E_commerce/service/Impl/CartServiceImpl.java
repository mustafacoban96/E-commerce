package com.shepherd.E_commerce.service.Impl;



import com.shepherd.E_commerce.dto.requests.CartItem;
import com.shepherd.E_commerce.exceptions.CartNotFoundException;
import com.shepherd.E_commerce.models.Products;
import com.shepherd.E_commerce.service.ProductService;
import org.springframework.stereotype.Service;
import com.shepherd.E_commerce.models.Cart;
import com.shepherd.E_commerce.models.User;
import com.shepherd.E_commerce.repository.CartRepository;
import com.shepherd.E_commerce.service.CartService;
import com.shepherd.E_commerce.service.UserService;
import com.shepherd.E_commerce.service.securityService.JwtService;

import java.util.Optional;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService{
	
	private final CartRepository cartRepository;
	private final JwtService jwtService;
	private final UserService userService;

	private final ProductService productService;
	
	public CartServiceImpl(
			CartRepository cartRepository,
			JwtService jwtService,
			UserService userService,
			ProductService productService) {
		this.cartRepository = cartRepository;
		this.jwtService = jwtService;
		this.userService = userService;
		this.productService = productService;
	}
	
	
	@Override
	public void addItemToCart(CartItem request, String access_token) {
		// Extract email from access token
		String email = jwtService.extractEmail(access_token);

		// Get user ID from email
		UUID user_id = userService.getUserByEmailAsEntity(email).getId();

		// Fetch the user's cart or throw an exception if not found
		Cart cart = (Cart) cartRepository.findByUserId(user_id)
				.orElseThrow(() -> new CartNotFoundException("Cart not found"));

		// Find the product by ID
		Products product = productService.findProductById(request.cart_id());

		// Add the product to the cart's items
		cart.getCartItems().add(product);

		// Save the updated cart
		cartRepository.save(cart);
	}
	

	@Override
	public Cart newCart(User user) {
		Cart cart = Cart.builder()
				.cartItems(null)
				.user(user)
				.build();
		
		return cartRepository.save(cart);
	}

}
