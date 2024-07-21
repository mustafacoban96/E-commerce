package com.shepherd.E_commerce.service.Impl;



import org.springframework.stereotype.Service;
import com.shepherd.E_commerce.dto.requests.AddItemToCartRequest;
import com.shepherd.E_commerce.models.Cart;
import com.shepherd.E_commerce.models.User;
import com.shepherd.E_commerce.repository.CartRepository;
import com.shepherd.E_commerce.service.CartService;
import com.shepherd.E_commerce.service.UserService;
import com.shepherd.E_commerce.service.securityService.JwtService;

@Service
public class CartServiceImpl implements CartService{
	
	private final CartRepository cartRepository;
	private final JwtService jwtService;
	private final UserService userService;
	
	public CartServiceImpl(CartRepository cartRepository,JwtService jwtService,UserService userService) {
		this.cartRepository = cartRepository;
		this.jwtService = jwtService;
		this.userService = userService;
	}
	
	
	@Override
	public void addItemToCart(AddItemToCartRequest request,String access_token) {
		String email = jwtService.extractEmail(access_token);
		User user = userService.getUserByEmailAsEntity(email);
		
		Cart cart = Cart.builder()
				.cartItems(request.getCart_items())
				.user(user)
				.build();
		
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
