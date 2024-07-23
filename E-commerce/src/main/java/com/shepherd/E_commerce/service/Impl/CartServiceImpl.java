package com.shepherd.E_commerce.service.Impl;



import com.shepherd.E_commerce.dto.requests.CartItem;
import com.shepherd.E_commerce.dto.response.IndividualCartItemResponse;
import com.shepherd.E_commerce.exceptions.CartNotFoundException;
import com.shepherd.E_commerce.mappers.ProductsMapper;
import com.shepherd.E_commerce.models.Products;
import com.shepherd.E_commerce.service.ProductService;
import org.springframework.stereotype.Service;
import com.shepherd.E_commerce.models.Cart;
import com.shepherd.E_commerce.models.User;
import com.shepherd.E_commerce.repository.CartRepository;
import com.shepherd.E_commerce.service.CartService;
import com.shepherd.E_commerce.service.UserService;
import com.shepherd.E_commerce.service.securityService.JwtService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService{
	
	private final CartRepository cartRepository;
	private final JwtService jwtService;
	private final UserService userService;

	private final ProductService productService;
	private final ProductsMapper productsMapper;
	
	public CartServiceImpl(
			CartRepository cartRepository,
			JwtService jwtService,
			UserService userService,
			ProductService productService,
			ProductsMapper productsMapper) {
		this.cartRepository = cartRepository;
		this.jwtService = jwtService;
		this.userService = userService;
		this.productService = productService;
		this.productsMapper = productsMapper;
	}
	
	
	@Override
	public void addItemToCart(CartItem request, String access_token) {
		String email = jwtService.extractEmail(access_token);
		UUID user_id = userService.getUserByEmailAsEntity(email).getId();
		Cart cart = (Cart) cartRepository.findByUserId(user_id)
				.orElseThrow(() -> new CartNotFoundException("Cart not found"));
		Products product = productService.findProductById(request.cart_item_id());
		cart.getCartItems().add(product);
		cartRepository.save(cart);
	}

	@Override
	public void removeItemFromCart(CartItem request, String access_token) {
		String email = jwtService.extractEmail(access_token);
		UUID user_id = userService.getUserByEmailAsEntity(email).getId();
		Cart cart = (Cart) cartRepository.findByUserId(user_id)
				.orElseThrow(() -> new CartNotFoundException("Cart not found"));
		Products product = productService.findProductById(request.cart_item_id());
		cart.getCartItems().remove(product);
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

	@Override
	public List<IndividualCartItemResponse> getAllCartItemByUserId(String access_token) {
		String email = jwtService.extractEmail(access_token);
		UUID user_id = userService.getUserByEmailAsEntity(email).getId();
		Cart cart = (Cart) cartRepository.findByUserId(user_id)
				.orElseThrow(() -> new CartNotFoundException("Cart not found"));
		List<Products> cartItemsList = cartRepository.findProductsByCartId(cart.getId());

		List<IndividualCartItemResponse> cartItemResponseList = cartItemsList.stream().map(product ->
				productsMapper.productToIndividualCartItem(product)).collect(Collectors.toList());

		return cartItemResponseList;
	}




}
