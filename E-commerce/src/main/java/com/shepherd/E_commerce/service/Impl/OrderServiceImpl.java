package com.shepherd.E_commerce.service.Impl;

import com.shepherd.E_commerce.dto.requests.OrderItemRequest;
import com.shepherd.E_commerce.dto.requests.OrderRequest;
import com.shepherd.E_commerce.models.OrderItems;
import com.shepherd.E_commerce.models.Orders;
import com.shepherd.E_commerce.repository.OrdersRepository;
import com.shepherd.E_commerce.service.OrderItemsService;
import com.shepherd.E_commerce.service.UserService;
import org.hibernate.query.Order;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.shepherd.E_commerce.service.OrderService;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService{

    private final UserService userService;
    private final OrderItemsService orderItemsService;

    private final OrdersRepository ordersRepository;

    public OrderServiceImpl(UserService userService,
                            @Lazy OrderItemsService orderItemsService,
                            OrdersRepository ordersRepository) {
        this.userService = userService;
        this.orderItemsService = orderItemsService;
        this.ordersRepository = ordersRepository;
    }

    //create order
    @Override
    @Transactional
    public Orders createOrder(OrderRequest request) {

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
        return ordersRepository.save(orders);
    }

    @Override
    public Orders getOrderById(UUID order_id) {
        return ordersRepository.getReferenceById(order_id);
    }
    //////////////////////////////////////////////////////////create order
}
