package com.shepherd.E_commerce.service;


import com.shepherd.E_commerce.dto.requests.CartItem;
import com.shepherd.E_commerce.dto.response.IndividualCartItemResponse;
import com.shepherd.E_commerce.exceptions.CartNotFoundException;
import com.shepherd.E_commerce.mappers.ProductsMapper;
import com.shepherd.E_commerce.models.Cart;
import com.shepherd.E_commerce.models.Products;
import com.shepherd.E_commerce.models.User;
import com.shepherd.E_commerce.repository.CartRepository;
import com.shepherd.E_commerce.service.Impl.CartServiceImpl;
import com.shepherd.E_commerce.service.securityService.JwtService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTests {

    private CartRepository cartRepository;
    private JwtService jwtService;
    private UserService userService;
    private ProductService productService;
    private ProductsMapper productsMapper;

    private CartServiceImpl cartService;

    private String access_token;

    @BeforeEach
    void setUp(){
        cartRepository = Mockito.mock(CartRepository.class);
        jwtService = Mockito.mock(JwtService.class);
        userService = Mockito.mock(UserService.class);
        productService = Mockito.mock(ProductService.class);
        productsMapper = Mockito.mock(ProductsMapper.class);
        access_token = "UAzRJl5QPMvqu0ljgEcIsaFNZlanS9o40v4pamRqJ5Qwy6DD0e21NrUPNKh8dmtHC5psHN2KQImVc56gf1VqSyQ3rCwUua4xAqr2nPwoH6UeoEkOvCEk7kmJfObrSBz58gQjfwPWvK/CvWM664tL1I7C4+FecedWVqCWP0vtcPQ=";

        cartService = new CartServiceImpl(
                cartRepository,
                jwtService,
                userService,
                productService,
                productsMapper
        );
    }

    @Test
    void testNewCart(){
        User user = User.builder()
                .id(UUID.randomUUID())
                .build();

        Cart expectedCart = Cart.builder()
                .user(user)
                .cartItems(null)
                .build();

        when(cartRepository.save(Mockito.any(Cart.class))).thenReturn(expectedCart);

        Cart actualCart = cartService.newCart(user);

        Assertions.assertThat(actualCart).isEqualTo(expectedCart);
        Assertions.assertThat(actualCart).isNotNull();
        Assertions.assertThat(actualCart.getCartItems()).isNull();

        verify(cartRepository,times(1)).save(any(Cart.class));

    }

    @Test
    void testAddItemToCartSuccessfully() {
        // Arrange
        CartItem reqParam1 = new CartItem(UUID.randomUUID());
        String email = "mustafa@gmail.com";
        UUID user_id = UUID.randomUUID();

        User user = User.builder()
                .id(user_id)
                .build();
        Products product = Products.builder().build();
        Set<Products> mySet = new HashSet<>();
        mySet.add(product);
        Cart cart = Cart.builder().cartItems(mySet).build();


        // Mock the behavior of the dependencies
        when(jwtService.extractEmail(access_token)).thenReturn(email);
        when(userService.getUserByEmailAsEntity(email)).thenReturn(user);  // Return a User object
        when(cartRepository.findByUserId(user_id)).thenReturn(Optional.of(cart));
        when(productService.findProductById(reqParam1.cart_item_id())).thenReturn(product);

        // Act
        cartService.addItemToCart(reqParam1, access_token);

        // Assert
        // Verify that the product was added to the cart
        Assertions.assertThat(cart.getCartItems()).contains(product);

        // Verify that the cart was saved
        verify(cartRepository, times(1)).save(cart);

        // Verify the interactions with mocked services
        verify(jwtService, times(1)).extractEmail(access_token);
        verify(userService, times(1)).getUserByEmailAsEntity(email);
        verify(cartRepository, times(1)).findByUserId(user_id);
        verify(productService, times(1)).findProductById(reqParam1.cart_item_id());
    }

    @Test
    void whenAddItemToCart_shouldThrowCartNotFoundException() {
        // Arrange
        CartItem request = new CartItem(UUID.randomUUID());
        String email = "test@example.com";
        UUID user_id = UUID.randomUUID();

        // Mock behavior
        when(jwtService.extractEmail(access_token)).thenReturn(email);
        when(userService.getUserByEmailAsEntity(email)).thenReturn(User.builder().email(email).id(user_id).build());
        when(cartRepository.findByUserId(user_id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CartNotFoundException.class, () ->
                cartService.addItemToCart(request, access_token)
        );

        // Verify that save is never called since the cart was not found
        verify(cartRepository, times(0)).save(Mockito.any());

        // Verify the interactions with the mocked services
        verify(jwtService, times(1)).extractEmail(access_token);
        verify(userService, times(1)).getUserByEmailAsEntity(email);
        verify(cartRepository, times(1)).findByUserId(user_id);
        verify(productService, times(0)).findProductById(request.cart_item_id());
    }


    @Test
    void testRemoveItemFromCartSuccessfully() {
        // Arrange
        CartItem reqParam1 = new CartItem(UUID.randomUUID());
        String email = "mustafa@gmail.com";
        UUID user_id = UUID.randomUUID();

        User user = User.builder()
                .id(user_id)
                .build();
        Products product = Products.builder().build();
        Set<Products> mySet = new HashSet<>();
        mySet.add(product);
        Cart cart = Cart.builder().cartItems(mySet).build();


        // Mock the behavior of the dependencies
        when(jwtService.extractEmail(access_token)).thenReturn(email);
        when(userService.getUserByEmailAsEntity(email)).thenReturn(user);  // Return a User object
        when(cartRepository.findByUserId(user_id)).thenReturn(Optional.of(cart));
        when(productService.findProductById(reqParam1.cart_item_id())).thenReturn(product);

        // Act
        cartService.removeItemFromCart(reqParam1, access_token);

        // Assert
        // Verify that the product was added to the cart
        Assertions.assertThat(cart.getCartItems()).isEmpty();

        // Verify that the cart was saved
        verify(cartRepository, times(1)).save(cart);

        // Verify the interactions with mocked services
        verify(jwtService, times(1)).extractEmail(access_token);
        verify(userService, times(1)).getUserByEmailAsEntity(email);
        verify(cartRepository, times(1)).findByUserId(user_id);
        verify(productService, times(1)).findProductById(reqParam1.cart_item_id());
    }



    @Test
    void whenRemoveItemFromCart_shouldThrowCartNotFoundException() {
        // Arrange
        CartItem request = new CartItem(UUID.randomUUID());
        String email = "test@example.com";
        UUID user_id = UUID.randomUUID();

        // Mock behavior
        when(jwtService.extractEmail(access_token)).thenReturn(email);
        when(userService.getUserByEmailAsEntity(email)).thenReturn(User.builder().email(email).id(user_id).build());
        when(cartRepository.findByUserId(user_id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CartNotFoundException.class, () ->
                cartService.removeItemFromCart(request, access_token)
        );

        // Verify that save is never called since the cart was not found
        verify(cartRepository, times(0)).save(Mockito.any());

        // Verify the interactions with the mocked services
        verify(jwtService, times(1)).extractEmail(access_token);
        verify(userService, times(1)).getUserByEmailAsEntity(email);
        verify(cartRepository, times(1)).findByUserId(user_id);
        verify(productService, times(0)).findProductById(request.cart_item_id());
    }



    @Test
    void testRemoveAllItemFromCartSuccessfully(){
        UUID user_id = UUID.randomUUID();
        User user = User.builder().id(user_id).build();
        Products products = Products.builder().name("aaa").build();
        Products products2 = Products.builder().name("bbb").build();
        Cart cart = Cart.builder().user(user).cartItems(new HashSet<>(Set.of(products,products2))).build();

        when(cartRepository.findByUserId(Mockito.any(UUID.class))).thenReturn(Optional.of(cart));

        cartService.removeAllItemsFromCart(user_id);

        Assertions.assertThat(cart.getCartItems()).isEmpty();

        verify(cartRepository,times(1)).findByUserId(Mockito.any(UUID.class));
        verify(cartRepository,times(1)).save(Mockito.any(Cart.class));


    }

    @Test
    void whenRemoveAllItemsFromCart_shouldThrowCartNotFoundException() {
        // Arrange
        UUID user_id = UUID.randomUUID();

        // Mock behavior: cartRepository returns Optional.empty()
        when(cartRepository.findByUserId(user_id)).thenReturn(Optional.empty());

        // Act & Assert
                assertThrows(CartNotFoundException.class, () ->
                cartService.removeAllItemsFromCart(user_id)
        );
        // Verify interactions with the mock
        verify(cartRepository, times(1)).findByUserId(user_id);
        verify(cartRepository, times(0)).save(any(Cart.class));
    }

    @Test
    void whenGetAllCartItemByUserId_shouldReturnListIndividualCartItemResponse() {
        // Arrange
        String email = "mustafa@gmail.com";
        UUID user_id = UUID.randomUUID();
        UUID cart_id = UUID.randomUUID();

        Products product1 = Products.builder().id(UUID.randomUUID()).name("aa").build();
        Products product2 = Products.builder().id(UUID.randomUUID()).name("bb").build();

        User user = User.builder().id(user_id).build();
        Cart cart = Cart.builder()
                .id(cart_id)
                .user(user)
                .cartItems(new HashSet<>(Set.of(product1, product2)))
                .build();

        IndividualCartItemResponse response1 = IndividualCartItemResponse.builder()
                .id(product1.getId())
                .name("aa")
                .stock(1)
                .price(product1.getPrice())
                .build();
        IndividualCartItemResponse response2 = IndividualCartItemResponse.builder()
                .id(product2.getId())
                .name("aa")
                .stock(1)
                .price(product2.getPrice())
                .build();



        when(jwtService.extractEmail(access_token)).thenReturn(email);
        when(userService.getUserByEmailAsEntity(email)).thenReturn(user);
        when(cartRepository.findByUserId(user_id)).thenReturn(Optional.of(cart));
        when(cartRepository.findProductsByCartId(cart_id)).thenReturn(List.of(product1, product2));
        when(productsMapper.productToIndividualCartItem(product1)).thenReturn(response1);
        when(productsMapper.productToIndividualCartItem(product2)).thenReturn(response2);

        // Act
        List<IndividualCartItemResponse> result = cartService.getAllCartItemByUserId(access_token);

        // Assert
        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result).containsExactlyInAnyOrder(response1, response2);

        verify(jwtService, times(1)).extractEmail(access_token);
        verify(userService, times(1)).getUserByEmailAsEntity(email);
        verify(cartRepository, times(1)).findByUserId(user_id);
        verify(cartRepository, times(1)).findProductsByCartId(cart_id);
        verify(productsMapper, times(1)).productToIndividualCartItem(product1);
        verify(productsMapper, times(1)).productToIndividualCartItem(product2);
    }

    @Test
    void whenCartNotFound_shouldThrowCartNotFoundException() {
        // Arrange
        String email = "mustafa@gmail.com";
        UUID user_id = UUID.randomUUID();

        when(jwtService.extractEmail(access_token)).thenReturn(email);
        when(userService.getUserByEmailAsEntity(email)).thenReturn(User.builder().id(user_id).build());
        when(cartRepository.findByUserId(user_id)).thenReturn(Optional.empty());

        // Act & Assert
        org.junit.jupiter.api.Assertions.assertThrows(CartNotFoundException.class,() -> cartService.getAllCartItemByUserId(access_token));


        verify(jwtService, times(1)).extractEmail(access_token);
        verify(userService, times(1)).getUserByEmailAsEntity(email);
        verify(cartRepository, times(1)).findByUserId(user_id);
    }




}
