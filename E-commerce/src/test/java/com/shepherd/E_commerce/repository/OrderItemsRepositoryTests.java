package com.shepherd.E_commerce.repository;


import com.shepherd.E_commerce.models.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class OrderItemsRepositoryTests {

    @Autowired
    OrderItemsRepository orderItemsRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    ProductRepository productRepository;

   private Orders order;
   private  User user;
   private Products product;

   private Products product2;

    @BeforeEach
    void setUp(){
        user = User.builder()
                .username("exampleUser")
                .password("password")
                .email("user@example.com")
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .isEnabled(true)
                .authorities(Set.of(Roles.ROLE_USER))  // Assign the ROLE_USER to this user
                .build();

        order = Orders.builder()
                .user(user)
                .build();

        product = Products.builder()
                .name("msi")
                .description("laptop")
                .price(Long.valueOf(1200))
                .stock(12)
                .build();

        product2 = Products.builder()
                .name("msi")
                .description("laptop")
                .price(Long.valueOf(1200))
                .stock(12)
                .build();

        userRepository.save(user);
        ordersRepository.save(order);
        productRepository.save(product);
        productRepository.save(product2);

    }


    @Test
    void shouldSaveOrderItems(){
        OrderItems orderItems = OrderItems.builder()
                .name("aaa")
                .order(order)
                .total_price(Long.valueOf(1222))
                .unit_price(Long.valueOf(111))
                .quantity(3)
                .products(product)
                .build();

        OrderItems insertedOrderItem =orderItemsRepository.save(orderItems);

        assertThat(insertedOrderItem).isNotNull();

    }
    @Test
    void testFindByOrderId() {
        // Create and save some OrderItems
        OrderItems item1 = OrderItems.builder()
                .id(UUID.randomUUID())
                .order(order)
                .name("Product 1")
                .quantity(2)
                .unit_price(100L)
                .total_price(200L)
                .products(product)
                .build();

        OrderItems item2 = OrderItems.builder()
                .id(UUID.randomUUID())
                .order(order)
                .name("Product 2")
                .quantity(1)
                .unit_price(300L)
                .total_price(300L)
                .products(product2)
                .build();

        orderItemsRepository.save(item1);
        orderItemsRepository.save(item2);

        // Test findByOrderId
        List<OrderItems> foundItems = orderItemsRepository.findByOrderId(order.getId());

        // Assertions
        assertThat(foundItems).isNotNull();
        assertThat(foundItems.size()).isEqualTo(2);
        assertThat(foundItems.get(0).getName()).isEqualTo("Product 1");
        assertThat(foundItems.get(1).getName()).isEqualTo("Product 2");
    }

    @Test
    void shouldDeleteOrderItems(){
        OrderItems orderItems = OrderItems.builder()
                .name("aaa")
                .order(order)
                .total_price(Long.valueOf(1222))
                .unit_price(Long.valueOf(111))
                .quantity(3)
                .products(product)
                .build();

        orderItemsRepository.save(orderItems);
        
        orderItemsRepository.deleteById(orderItems.getId());

        Optional<OrderItems> deletedOrderItems = orderItemsRepository.findById(orderItems.getId());

        assertThat(deletedOrderItems).isEmpty();

    }



}
