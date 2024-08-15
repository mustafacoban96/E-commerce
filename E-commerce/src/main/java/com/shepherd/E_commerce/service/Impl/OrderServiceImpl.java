package com.shepherd.E_commerce.service.Impl;

import com.shepherd.E_commerce.dto.requests.OrderItemRequest;
import com.shepherd.E_commerce.dto.requests.OrderRequest;
import com.shepherd.E_commerce.dto.response.CreateOrderResponse;
import com.shepherd.E_commerce.dto.response.OrderResponse;
import com.shepherd.E_commerce.exceptions.NoOrdersFoundException;
import com.shepherd.E_commerce.mappers.OrderMapper;
import com.shepherd.E_commerce.models.OrderItems;
import com.shepherd.E_commerce.models.Orders;
import com.shepherd.E_commerce.repository.OrdersRepository;
import com.shepherd.E_commerce.service.CartService;
import com.shepherd.E_commerce.service.OrderItemsService;
import com.shepherd.E_commerce.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.shepherd.E_commerce.service.OrderService;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{

    private final UserService userService;
    private final OrderItemsService orderItemsService;
    private final CartService cartService;
    private final OrdersRepository ordersRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(UserService userService,
                            @Lazy OrderItemsService orderItemsService,
                            OrdersRepository ordersRepository,
                            CartService cartService,
                            OrderMapper orderMapper) {
        this.userService = userService;
        this.orderItemsService = orderItemsService;
        this.ordersRepository = ordersRepository;
        this.cartService = cartService;
        this.orderMapper = orderMapper;
    }

    //create order
    @Override
    @Transactional
    public CreateOrderResponse createOrder(OrderRequest request) {

        Set<OrderItems> items = new HashSet<>();
        Orders orders = Orders.builder()
                .total_price(request.getTotal_price())
                .user(userService.getUserByIdAsEntity(request.getUser_id()))
                .orderItems(items)
                .build();
        ordersRepository.save(orders);
        //System.out.println("Hedef ustanın elinde:::::" + orders.getId());
        for(OrderItemRequest item : request.getOrder_items_list()){
            items.add(orderItemsService.createOrderItem(item,orders.getId()));
        }
        orders.setOrderItems(items);
        cartService.removeAllItemsFromCart(request.getUser_id());
        ordersRepository.save(orders);
        ordersRepository.flush(); // Force save to the database

        //System.out.println("qqwe" + orders.getCreated_at().getTime());

        return orderMapper.toCreateOrderResponse(orders) ;
    }

    @Override
    public Orders getOrderById(UUID order_id) {
        return ordersRepository.getReferenceById(order_id);
    }

    @Override
    @Transactional
    public List<OrderResponse> getAllOrderByUserId(UUID user_id) {
        List<Orders> listOrder = ordersRepository.findByUser_Id(user_id);

        if (listOrder.isEmpty()) {
            throw new NoOrdersFoundException("No orders found for user");
        }

        return listOrder.stream()
                .map(order -> orderMapper.toOrderResponse(order))
                .collect(Collectors.toList());


    }
    //////////////////////////////////////////////////////////create order
}
