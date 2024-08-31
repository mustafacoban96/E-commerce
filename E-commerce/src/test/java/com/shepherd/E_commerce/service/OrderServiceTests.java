package com.shepherd.E_commerce.service;


import com.shepherd.E_commerce.dto.requests.OrderItemRequest;
import com.shepherd.E_commerce.dto.requests.OrderRequest;
import com.shepherd.E_commerce.dto.response.CreateOrderResponse;
import com.shepherd.E_commerce.dto.response.OrderResponse;
import com.shepherd.E_commerce.exceptions.NoOrdersFoundException;
import com.shepherd.E_commerce.mappers.OrderMapper;
import com.shepherd.E_commerce.models.Orders;
import com.shepherd.E_commerce.models.User;
import com.shepherd.E_commerce.repository.OrdersRepository;
import com.shepherd.E_commerce.service.Impl.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTests {

    private UserService userService;
    private OrderItemsService orderItemsService;
    private CartService cartService;
    private OrdersRepository ordersRepository;
    private OrderMapper orderMapper;

    private OrderServiceImpl orderService;



    @BeforeEach
    void setUp(){
        userService = Mockito.mock(UserService.class);
        orderItemsService = Mockito.mock(OrderItemsService.class);
        ordersRepository = Mockito.mock(OrdersRepository.class);
        orderMapper = Mockito.mock(OrderMapper.class);
        cartService = Mockito.mock(CartService.class);
        orderService = new OrderServiceImpl(userService,orderItemsService,ordersRepository,cartService,orderMapper);

    }


    @Test
    @Transactional
    void whenCreateOrder_shouldReturnCreateOrderResponse() {
        UUID user_id = UUID.randomUUID();
        UUID order_id = UUID.randomUUID();
        Long total_price = 40L;
        String orderStatus = "COMPLETED";
        Boolean isSuccess = true;

        // OrderItemRequest objects
        OrderItemRequest orderItemRequest1 = OrderItemRequest.builder()
                .product_id(UUID.randomUUID())
                .product_name("AA")
                .quantity(2)
                .unit_price(10L)
                .total_price_per_product(20L)
                .build();
        OrderItemRequest orderItemRequest2 = OrderItemRequest.builder()
                .product_id(UUID.randomUUID())
                .product_name("BB")
                .quantity(2)
                .unit_price(10L)
                .total_price_per_product(20L)
                .build();

        // User object
        User user = User.builder()
                .id(user_id)
                .build();

        // OrderRequest object
        OrderRequest request = new OrderRequest();
        request.setOrder_items_list(Set.of(orderItemRequest1, orderItemRequest2));
        request.setUser_id(user.getId());
        request.setTotal_price(total_price);

        // Orders object without setting the ID manually
       /* Orders order = Orders.builder()
                .total_price(request.getTotal_price())
                .user(user)
                .orderItems(new HashSet<>())
                .build();*/

        // Stubbing for User and Orders
        when(userService.getUserByIdAsEntity(request.getUser_id())).thenReturn(user);
        when(ordersRepository.save(any(Orders.class))).thenAnswer(invocation -> {
            Orders savedOrder = invocation.getArgument(0);
            savedOrder.setId(order_id); // Mock the ID generation
            return savedOrder;
        });

        // Stubbing for OrderItems
       /* OrderItems orderItems1 = new OrderItems();
        OrderItems orderItems2 = new OrderItems();
        when(orderItemsService.createOrderItem(orderItemRequest1, order_id)).thenReturn(orderItems1);
        when(orderItemsService.createOrderItem(orderItemRequest2, order_id)).thenReturn(orderItems2);*/

        // Creating the CreateOrderResponse object with required arguments
        CreateOrderResponse response = new CreateOrderResponse(order_id, orderStatus, total_price, isSuccess);
        when(orderMapper.toCreateOrderResponse(any(Orders.class))).thenReturn(response);

        // Run the method
        CreateOrderResponse orderResponse = orderService.createOrder(request);

        // Assertions
        Assertions.assertThat(orderResponse).isNotNull();
        assertEquals(response, orderResponse);

        // Verify interactions
        verify(userService, times(1)).getUserByIdAsEntity(request.getUser_id());
        verify(ordersRepository, times(2)).save(any(Orders.class));
        verify(ordersRepository, times(1)).flush();
        verify(cartService, times(1)).removeAllItemsFromCart(request.getUser_id());
    }

    @Test
    void whenGetOrderById_shouldReturnOrder() {
        // Arrange
        UUID orderId = UUID.randomUUID();
        Orders expectedOrder = Orders.builder()
                .id(orderId)
                .total_price(100L)
                .build();

        // Stub: ordersRepository.getReferenceById(order_id) çağrıldığında expectedOrder'ı döndür
        when(ordersRepository.getReferenceById(orderId)).thenReturn(expectedOrder);

        // Act: orderService.getOrderById metodu çağrılır
        Orders actualOrder = orderService.getOrderById(orderId);

        // Assert: Dönen değerin expectedOrder'a eşit olup olmadığını kontrol ederiz
        assertNotNull(actualOrder);
        assertEquals(expectedOrder, actualOrder);
        assertEquals(orderId, actualOrder.getId());
    }



    @Test
    void whenGetAllOrderByUserId_shouldReturnOrderResponses() {
        // Arrange
        UUID userId = UUID.randomUUID();
        Orders order1 = Orders.builder().id(UUID.randomUUID()).total_price(100L).build();
        Orders order2 = Orders.builder().id(UUID.randomUUID()).total_price(200L).build();
        List<Orders> listOrder = List.of(order1, order2);

        OrderResponse response1 = new OrderResponse(UUID.randomUUID(), "Order 1", 100L);
        OrderResponse response2 = new OrderResponse(UUID.randomUUID(), "Order 2", 200L);

        when(ordersRepository.findByUserId(userId)).thenReturn(listOrder);
        when(orderMapper.toOrderResponse(order1)).thenReturn(response1);
        when(orderMapper.toOrderResponse(order2)).thenReturn(response2);

        // Act
        List<OrderResponse> result = orderService.getAllOrderByUserId(userId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(response1, result.get(0));
        assertEquals(response2, result.get(1));

        // Verify
        verify(ordersRepository, times(1)).findByUserId(userId);
        verify(orderMapper, times(2)).toOrderResponse(any(Orders.class));
    }

    @Test
    void whenGetAllOrderByUserId_shouldThrowNoOrdersFoundException() {
        // Arrange
        UUID userId = UUID.randomUUID();
        when(ordersRepository.findByUserId(userId)).thenReturn(Collections.emptyList());
        // Act & Assert
        assertThrows(NoOrdersFoundException.class, () -> {
            orderService.getAllOrderByUserId(userId);
        });
        // Verify
        verify(ordersRepository, times(1)).findByUserId(userId);
        verify(orderMapper, never()).toOrderResponse(any(Orders.class));
    }



}
