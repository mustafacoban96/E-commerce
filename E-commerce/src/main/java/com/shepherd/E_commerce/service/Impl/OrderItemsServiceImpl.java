package com.shepherd.E_commerce.service.Impl;

import com.shepherd.E_commerce.dto.requests.OrderItemRequest;
import com.shepherd.E_commerce.service.OrderService;
import com.shepherd.E_commerce.service.ProductService;
import org.springframework.stereotype.Service;


import com.shepherd.E_commerce.models.OrderItems;
import com.shepherd.E_commerce.repository.OrderItemsRepository;
import com.shepherd.E_commerce.service.OrderItemsService;

import java.util.UUID;

@Service
public class OrderItemsServiceImpl implements OrderItemsService{

    private final OrderItemsRepository orderItemsRepository;
    private final ProductService productService;

    private final OrderService orderService;
    public OrderItemsServiceImpl(OrderItemsRepository orderItemsRepository,
                                 ProductService productService,
                                 OrderService orderService) {
        this.orderItemsRepository = orderItemsRepository;
        this.productService = productService;
        this.orderService = orderService;
    }

    @Override
    public OrderItems createOrderItem(OrderItemRequest request, UUID order_id) {

        OrderItems item = OrderItems.builder()
                .name(request.getProduct_name())
                .quantity(request.getQuantity())
                .unit_price(request.getUnit_price())
                .total_price(request.getTotal_price_per_product())
                .products(productService.findProductById(request.getProduct_id()))
                .build();

        item.setOrder(orderService.getOrderById(order_id));
        return orderItemsRepository.save(item);
    }
}
