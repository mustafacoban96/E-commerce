package com.shepherd.E_commerce.service;


import com.shepherd.E_commerce.dto.requests.CreateProductRequest;
import com.shepherd.E_commerce.dto.requests.UpdateProductRequest;
import com.shepherd.E_commerce.dto.response.GetProductByIdResponse;
import com.shepherd.E_commerce.dto.response.ProductListResponse;
import com.shepherd.E_commerce.dto.response.ProductListResponseV2;
import com.shepherd.E_commerce.dto.response.ProductUpdateResponse;
import com.shepherd.E_commerce.exceptions.ProductNotFoundException;
import com.shepherd.E_commerce.mappers.ProductsMapper;
import com.shepherd.E_commerce.models.Products;
import com.shepherd.E_commerce.repository.ProductRepository;
import com.shepherd.E_commerce.service.Impl.ProductsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {

    private ProductRepository productRepository;

    private ProductsMapper productsMapper;

    private ProductsServiceImpl productsService;


    @BeforeEach
    void setUp(){
        productRepository = Mockito.mock(ProductRepository.class);
        productsMapper = Mockito.mock(ProductsMapper.class);
        productsService = new ProductsServiceImpl(productRepository,productsMapper);
    }

    @Test
    public void testCreateProduct() {
        // Arrange
        CreateProductRequest request = new CreateProductRequest("Test Product", "This is a test product", 100, 2900L);

        Products expectedProduct = Products.builder()
                .name(request.name())
                .description(request.description())
                .stock(request.stock())
                .price(request.price())
                .build();

        when(productRepository.save(Mockito.any(Products.class))).thenReturn(expectedProduct);

        // Act
        Products actualProduct = productsService.createProduct(request);

        // Assert
        assertEquals(expectedProduct.getName(), actualProduct.getName());
        assertEquals(expectedProduct.getDescription(), actualProduct.getDescription());
        assertEquals(expectedProduct.getStock(), actualProduct.getStock());
        assertEquals(expectedProduct.getPrice(), actualProduct.getPrice());
    }


    @Test
    void testGetAllProducts() {
        // Arrange
        Products product1 = Products.builder()
                .name("aaa")
                .description("bbb")
                .stock(23)
                .price(23L)
                .build();

        Products product2 = Products.builder()
                .name("qqq")
                .description("hhh")
                .stock(20)
                .price(233L)
                .build();

        ProductListResponse response1 = new ProductListResponse(product1.getId(),"aaa", "bbb", 23, 23L);
        ProductListResponse response2 = new ProductListResponse(product2.getId(),"qqq", "hhh", 20, 233L);

        when(productRepository.findAll()).thenReturn(List.of(product1, product2));
        when(productsMapper.ProductEntityToResponse(product1)).thenReturn(response1);
        when(productsMapper.ProductEntityToResponse(product2)).thenReturn(response2);

        // Act
        List<ProductListResponse> myList = productsService.getAllProducts();

        // Assert
        org.assertj.core.api.Assertions.assertThat(myList).isNotNull();
        org.assertj.core.api.Assertions.assertThat(myList).hasSize(2);
        org.assertj.core.api.Assertions.assertThat(myList).containsExactly(response1, response2);

        // Verify that the mapper was called for each product
        verify(productsMapper, times(1)).ProductEntityToResponse(product1);
        verify(productsMapper, times(1)).ProductEntityToResponse(product2);
    }

    @Test
    void testDeleteProductById_ProductExists() {
        // Arrange
        UUID productId = UUID.randomUUID();
        when(productRepository.existsById(productId)).thenReturn(true);

        // Act
        productsService.deleteProductById(productId);

        // Assert
        verify(productRepository, times(1)).existsById(productId);
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void testDeleteProductById_ProductDoesNotExist() {
        // Arrange
        UUID productId = UUID.randomUUID();
        when(productRepository.existsById(productId)).thenReturn(false);

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> {
            productsService.deleteProductById(productId);
        });

        verify(productRepository, times(1)).existsById(productId);
        verify(productRepository, never()).deleteById(productId);
    }

    @Test
    void testUpdateProductById_ProductExists() {
        // Arrange
        UUID productId = UUID.randomUUID();
        UpdateProductRequest request = new UpdateProductRequest("Updated Name", "Updated Description", 50, 100L);

        Products existingProduct = Products.builder()
                .id(productId)
                .name("Old Name")
                .description("Old Description")
                .stock(10)
                .price(20L)
                .build();

        Products updatedProduct = Products.builder()
                .id(productId)
                .name("Updated Name")
                .description("Updated Description")
                .stock(50)
                .price(100L)
                .build();

        ProductUpdateResponse expectedResponse = new ProductUpdateResponse(productId,"Updated Name", "Updated Description", 50, 100L);

        when(productRepository.existsById(productId)).thenReturn(true);
        when(productRepository.getReferenceById(productId)).thenReturn(existingProduct);
        when(productRepository.save(existingProduct)).thenReturn(updatedProduct);
        when(productsMapper.ProductEntityToProductUpdateResponse(updatedProduct)).thenReturn(expectedResponse);

        // Act
        ProductUpdateResponse actualResponse = productsService.updateProductById(productId, request);

        // Assert
        org.assertj.core.api.Assertions.assertThat(actualResponse).isNotNull();
        assertEquals(expectedResponse, actualResponse);

        verify(productRepository, times(1)).existsById(productId);
        verify(productRepository, times(1)).getReferenceById(productId);
        verify(productRepository, times(1)).save(existingProduct);
        verify(productsMapper, times(1)).ProductEntityToProductUpdateResponse(updatedProduct);
    }

    @Test
    void testUpdateProductById_ProductDoesNotExist() {
        // Arrange
        UUID productId = UUID.randomUUID();
        UpdateProductRequest request = new UpdateProductRequest("Updated Name", "Updated Description", 50, 100L);

        when(productRepository.existsById(productId)).thenReturn(false);

        // Act & Assert
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            productsService.updateProductById(productId, request);
        });



        verify(productRepository, times(1)).existsById(productId);
        verify(productRepository, never()).getReferenceById(any());
        verify(productRepository, never()).save(any());
        verify(productsMapper, never()).ProductEntityToProductUpdateResponse(any());
    }

    @Test
    void testFindProductById_ProductExists() {
        // Arrange
        UUID productId = UUID.randomUUID();
        Products expectedProduct = Products.builder()
                .id(productId)
                .name("Test Product")
                .description("This is a test product")
                .stock(100)
                .price(2900L)
                .build();

        when(productRepository.existsById(productId)).thenReturn(true);
        when(productRepository.getReferenceById(productId)).thenReturn(expectedProduct);

        // Act
        Products actualProduct = productsService.findProductById(productId);

        // Assert
        org.assertj.core.api.Assertions.assertThat(actualProduct).isNotNull();
        org.assertj.core.api.Assertions.assertThat(actualProduct.getId()).isEqualTo(productId);
        org.assertj.core.api.Assertions.assertThat(actualProduct.getName()).isEqualTo("Test Product");

        verify(productRepository, times(1)).existsById(productId);
        verify(productRepository, times(1)).getReferenceById(productId);
    }

    @Test
    void testFindProductById_ProductDoesNotExist() {
        // Arrange
        UUID productId = UUID.randomUUID();
        when(productRepository.existsById(productId)).thenReturn(false);

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> {
            productsService.findProductById(productId);
        });

        verify(productRepository, times(1)).existsById(productId);
        verify(productRepository, never()).getReferenceById(productId);
    }


    @Test
    void testGetProductById_ProductExists() {
        // Arrange
        UUID productId = UUID.randomUUID();
        Products product = Products.builder()
                .id(productId)
                .name("Test Product")
                .description("This is a test product")
                .stock(100)
                .price(2900L)
                .build();

        GetProductByIdResponse expectedResponse = new GetProductByIdResponse(
                productId,
                "Test Product",
                "This is a test product",
                100,
                2900L
        );

        when(productRepository.existsById(productId)).thenReturn(true);
        when(productRepository.getReferenceById(productId)).thenReturn(product);
        when(productsMapper.ProductEntityToResponseById(product)).thenReturn(expectedResponse);

        // Act
        GetProductByIdResponse actualResponse = productsService.getProductById(productId);

        // Assert
        org.assertj.core.api.Assertions.assertThat(actualResponse).isNotNull();
        org.assertj.core.api.Assertions.assertThat(actualResponse.id()).isEqualTo(productId);
        org.assertj.core.api.Assertions.assertThat(actualResponse.name()).isEqualTo("Test Product");

        verify(productRepository, times(1)).existsById(productId);
        verify(productRepository, times(1)).getReferenceById(productId);
        verify(productsMapper, times(1)).ProductEntityToResponseById(product);
    }

    @Test
    void testGetProductById_ProductDoesNotExist() {
        // Arrange
        UUID productId = UUID.randomUUID();
        when(productRepository.existsById(productId)).thenReturn(false);

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> {
            productsService.getProductById(productId);
        });

        verify(productRepository, times(1)).existsById(productId);
        verify(productRepository, never()).getReferenceById(productId);
        verify(productsMapper, never()).ProductEntityToResponseById(any());
    }

    @Test
    void testGetAllProductV2_WithValidPageRequest() {
        // Arrange
        int pageNo = 0;
        int pageSize = 5;

        Products product1 = Products.builder()
                .id(UUID.randomUUID())
                .name("Product 1")
                .description("Description 1")
                .stock(10)
                .price(100L)
                .build();

        Products product2 = Products.builder()
                .id(UUID.randomUUID())
                .name("Product 2")
                .description("Description 2")
                .stock(20)
                .price(200L)
                .build();

        ProductListResponse response1 = new ProductListResponse(
                product1.getId(),
                product1.getName(),
                product1.getDescription(),
                product1.getStock(),
                product1.getPrice()
        );

        ProductListResponse response2 = new ProductListResponse(
                product2.getId(),
                product2.getName(),
                product2.getDescription(),
                product2.getStock(),
                product2.getPrice()
        );

        // Simulate that there are 5 total products in the database, but only 2 are in the current page
        List<Products> productList = List.of(product1, product2);
        Page<Products> productPage = new PageImpl<>(productList, PageRequest.of(pageNo, pageSize), 5);

        when(productRepository.findAll(any(Pageable.class))).thenReturn(productPage);
        when(productsMapper.ProductEntityToResponse(product1)).thenReturn(response1);
        when(productsMapper.ProductEntityToResponse(product2)).thenReturn(response2);

        // Act
        ProductListResponseV2 productListResponseV2 = productsService.getAllProductV2(pageNo, pageSize);

        // Assert
        org.assertj.core.api.Assertions.assertThat(productListResponseV2).isNotNull();
        org.assertj.core.api.Assertions.assertThat(productListResponseV2.getContent()).hasSize(2);
        org.assertj.core.api.Assertions.assertThat(productListResponseV2.getContent()).containsExactly(response1, response2);
        org.assertj.core.api.Assertions.assertThat(productListResponseV2.getPageNo()).isEqualTo(pageNo);
        org.assertj.core.api.Assertions.assertThat(productListResponseV2.getPageSize()).isEqualTo(pageSize);
        org.assertj.core.api.Assertions.assertThat(productListResponseV2.getTotalElements()).isEqualTo(5);
        org.assertj.core.api.Assertions.assertThat(productListResponseV2.getTotalPages()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(productListResponseV2.getLast()).isTrue();

        verify(productRepository, times(1)).findAll(any(Pageable.class));
        verify(productsMapper, times(1)).ProductEntityToResponse(product1);
        verify(productsMapper, times(1)).ProductEntityToResponse(product2);
    }

    @Test
    void testGetAllProductV2_EmptyPage() {
        // Arrange
        int pageNo = 0;
        int pageSize = 5;

        // Create an empty page with a total element count of 0
        Page<Products> productPage = new PageImpl<>(List.of(), PageRequest.of(pageNo, pageSize), 0);

        when(productRepository.findAll(any(Pageable.class))).thenReturn(productPage);

        // Act
        ProductListResponseV2 productListResponseV2 = productsService.getAllProductV2(pageNo, pageSize);

        // Assert
        org.assertj.core.api.Assertions.assertThat(productListResponseV2).isNotNull();
        org.assertj.core.api.Assertions.assertThat(productListResponseV2.getContent()).isEmpty();
        org.assertj.core.api.Assertions.assertThat(productListResponseV2.getPageNo()).isEqualTo(pageNo);
        org.assertj.core.api.Assertions.assertThat(productListResponseV2.getPageSize()).isEqualTo(pageSize);
        org.assertj.core.api.Assertions.assertThat(productListResponseV2.getTotalElements()).isEqualTo(0);
        org.assertj.core.api.Assertions.assertThat(productListResponseV2.getTotalPages()).isEqualTo(0);
        org.assertj.core.api.Assertions.assertThat(productListResponseV2.getLast()).isTrue();

        verify(productRepository, times(1)).findAll(any(Pageable.class));
        verify(productsMapper, never()).ProductEntityToResponse(any());
    }


}
