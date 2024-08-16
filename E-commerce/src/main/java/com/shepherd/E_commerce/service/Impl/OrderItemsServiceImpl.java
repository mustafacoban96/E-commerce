package com.shepherd.E_commerce.service.Impl;

import com.shepherd.E_commerce.dto.requests.OrderItemRequest;
import com.shepherd.E_commerce.dto.response.OrderItemsByOrderIdResponse;
import com.shepherd.E_commerce.exceptions.NoOrdersFoundException;
import com.shepherd.E_commerce.exceptions.ProductStockException;
import com.shepherd.E_commerce.mappers.OrderItemsMapper;
import com.shepherd.E_commerce.models.Orders;
import com.shepherd.E_commerce.models.Products;
import com.shepherd.E_commerce.service.OrderService;
import com.shepherd.E_commerce.service.ProductService;
import org.springframework.stereotype.Service;


import com.shepherd.E_commerce.models.OrderItems;
import com.shepherd.E_commerce.repository.OrderItemsRepository;
import com.shepherd.E_commerce.service.OrderItemsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderItemsServiceImpl implements OrderItemsService{

    private final OrderItemsRepository orderItemsRepository;
    private final ProductService productService;

    private final OrderService orderService;

    private final OrderItemsMapper orderItemsMapper;
    public OrderItemsServiceImpl(OrderItemsRepository orderItemsRepository,
                                 ProductService productService,
                                 OrderService orderService,
                                 OrderItemsMapper orderItemsMapper) {
        this.orderItemsRepository = orderItemsRepository;
        this.productService = productService;
        this.orderService = orderService;
        this.orderItemsMapper = orderItemsMapper;
    }

    @Override
    public OrderItems createOrderItem(OrderItemRequest request, UUID order_id) {

        Products product = productService.findProductById(request.getProduct_id());

        if(request.getQuantity() > product.getStock() ){
            throw new ProductStockException("Product sold out");
        }


        OrderItems item = OrderItems.builder()
                .name(request.getProduct_name())
                .quantity(request.getQuantity())
                .unit_price(request.getUnit_price())
                .total_price(request.getTotal_price_per_product())
                .products(product)
                .build();
        product.setStock(product.getStock() - request.getQuantity());
        item.setOrder(orderService.getOrderById(order_id));
        return orderItemsRepository.save(item);
    }

    @Override
    @Transactional
    public List<OrderItemsByOrderIdResponse> getOrderItemsByOrderId(UUID order_id) {
        List<OrderItems> listOrderItems = orderItemsRepository.findByOrderId(order_id);

        if (listOrderItems.isEmpty()) {
            throw new NoOrdersFoundException("Not found order");
        }

        return listOrderItems.stream()
                .map(orderItem -> orderItemsMapper.toOrderItemsByOrderIdResponse(orderItem))
                .collect(Collectors.toList());

    }
}
