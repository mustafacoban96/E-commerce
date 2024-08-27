package com.shepherd.E_commerce.repository;


import com.shepherd.E_commerce.models.Products;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTests {

    @Autowired
    ProductRepository productRepository;

    @Test
    void shouldSaveProduct_whenValidProduct(){

        Products product = Products.builder()
                .name("msi")
                .description("laptop")
                .price(Long.valueOf(1200))
                .stock(12)
                .build();
        Products insertedProduct = productRepository.save(product);
        Assertions.assertThat(insertedProduct).isNotNull();
    }


    @Test
    void testFindById(){
        Products product = Products.builder()
                .name("msi")
                .description("laptop")
                .price(Long.valueOf(1200))
                .stock(12)
                .build();
        productRepository.save(product);
        Products insertedProduct = productRepository.findById(product.getId()).get();

        org.junit.jupiter.api.Assertions.assertEquals(product.getId(),insertedProduct.getId());

    }

    @Test
    void testFindAllProduct(){

        List<Products> productsList = productRepository.findAll();

        Assertions.assertThat(productsList).hasSizeGreaterThan(0);
    }

    @Test
    void testDeleteById(){

        Products product = Products.builder()
                .name("msi")
                .description("laptop")
                .price(Long.valueOf(1200))
                .stock(12)
                .build();

        productRepository.save(product);

        // Delete the product by its ID
        productRepository.deleteById(product.getId());

        // Verify that deleteById was called once with the correct ID
        Optional<Products> products2 = productRepository.findById(product.getId());

        Assertions.assertThat(products2).isEmpty();

    }
}

