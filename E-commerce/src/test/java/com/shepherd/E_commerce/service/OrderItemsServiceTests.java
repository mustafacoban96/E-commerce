package com.shepherd.E_commerce.service;


import com.shepherd.E_commerce.dto.requests.OrderItemRequest;
import com.shepherd.E_commerce.dto.response.OrderItemsByOrderIdResponse;
import com.shepherd.E_commerce.exceptions.NoOrdersFoundException;
import com.shepherd.E_commerce.exceptions.ProductStockException;
import com.shepherd.E_commerce.mappers.OrderItemsMapper;
import com.shepherd.E_commerce.models.OrderItems;
import com.shepherd.E_commerce.models.Orders;
import com.shepherd.E_commerce.models.Products;
import com.shepherd.E_commerce.repository.OrderItemsRepository;
import com.shepherd.E_commerce.service.Impl.OrderItemsServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderItemsServiceTests {

    private OrderItemsRepository orderItemsRepository;
    private ProductService productService;
    private OrderService orderService;
    private OrderItemsMapper orderItemsMapper;
    private OrderItemsServiceImpl orderItemsService;


    @BeforeEach
    void setUp(){
        orderItemsRepository = Mockito.mock(OrderItemsRepository.class);
        productService = Mockito.mock(ProductService.class);
        orderService = Mockito.mock(OrderService.class);
        orderItemsMapper = Mockito.mock(OrderItemsMapper.class);
        orderItemsService = new OrderItemsServiceImpl(
                orderItemsRepository,
                productService,
                orderService,
                orderItemsMapper
        );
    }
    @Test
    void testCreateOrderItem_Success() {
        // Arrange
        OrderItemRequest request = OrderItemRequest.builder()
                .product_id(UUID.randomUUID())
                .total_price_per_product(200L)
                .unit_price(100L)
                .quantity(2)
                .product_name("Test Product")
                .build();


        Products product = new Products();
        product.setStock(10);

        when(productService.findProductById(request.getProduct_id())).thenReturn(product);
        when(orderService.getOrderById(any(UUID.class))).thenReturn(new Orders());
        when(orderItemsRepository.save(any(OrderItems.class))).thenReturn(new OrderItems());

        // Act
        OrderItems orderItem = orderItemsService.createOrderItem(request, UUID.randomUUID());

        // Assert
        Assertions.assertThat(orderItem).isNotNull();
        Assertions.assertThat(product.getStock()).isEqualTo(8);

        verify(orderItemsRepository, times(1)).save(any(OrderItems.class));
    }


    @Test
    void testCreateOrderItem_ProductStockException() {
        // Arrange
        OrderItemRequest request = OrderItemRequest.builder()
                .product_id(UUID.randomUUID())
                .total_price_per_product(2000L)
                .unit_price(100L)
                .quantity(20)
                .product_name("Test Product")
                .build();

        Products product = new Products();
        product.setStock(5);

        when(productService.findProductById(request.getProduct_id())).thenReturn(product);

        // Act & Assert
        assertThrows(ProductStockException.class,
                ()->orderItemsService.createOrderItem(request, UUID.randomUUID()));

        verify(orderItemsRepository, Mockito.never()).save(any(OrderItems.class));
    }

    @Test
    @Transactional
    void testGetOrderItemsByOrderId_Success() {
        // Arrange
        UUID orderId = UUID.randomUUID();
        OrderItems orderItem1 = new OrderItems();
        OrderItems orderItem2 = new OrderItems();

        // Assume these are the values your OrderItemsByOrderIdResponse expects
        String productName = "Test Product";
        int quantity = 2;
        long unitPrice = 100L;
        long totalPrice = 200L;

        when(orderItemsRepository.findByOrderId(orderId)).thenReturn(Stream.of(orderItem1, orderItem2).collect(Collectors.toList()));
        when(orderItemsMapper.toOrderItemsByOrderIdResponse(any(OrderItems.class)))
                .thenReturn(new OrderItemsByOrderIdResponse(productName, quantity, unitPrice, totalPrice));

        // Act
        List<OrderItemsByOrderIdResponse> responses = orderItemsService.getOrderItemsByOrderId(orderId);

        // Assert
        Assertions.assertThat(responses).isNotNull();
        Assertions.assertThat(responses.size()).isEqualTo(2);
        verify(orderItemsRepository, times(1)).findByOrderId(orderId);
        verify(orderItemsMapper, times(2)).toOrderItemsByOrderIdResponse(any(OrderItems.class));
    }

    @Test
    @Transactional
    void testGetOrderItemsByOrderId_NoOrdersFoundException() {
        // Arrange
        UUID orderId = UUID.randomUUID();

        // Mock the repository to return an empty list
        when(orderItemsRepository.findByOrderId(orderId)).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(NoOrdersFoundException.class, () -> orderItemsService.getOrderItemsByOrderId(orderId));

        // Verify that the repository method is called once
        verify(orderItemsRepository, times(1)).findByOrderId(orderId);
        // Verify that the mapper is not called since no items are found
        verify(orderItemsMapper, never()).toOrderItemsByOrderIdResponse(any(OrderItems.class));
    }



}
