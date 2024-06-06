package com.shepherd.E_commerce.service.Impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shepherd.E_commerce.dto.requests.CreateProductRequest;
import com.shepherd.E_commerce.dto.requests.UpdateProductRequest;
import com.shepherd.E_commerce.dto.response.ProductListResponse;
import com.shepherd.E_commerce.dto.response.ProductUpdateResponse;
import com.shepherd.E_commerce.exceptions.ProductNotFoundException;
import com.shepherd.E_commerce.mappers.ProductsMapper;
import com.shepherd.E_commerce.models.Products;
import com.shepherd.E_commerce.repository.ProductRepository;
import com.shepherd.E_commerce.service.ProductServcie;

@Service
public class ProductsServiceImpl implements ProductServcie{
	
	private final ProductRepository productRepository;
	private final ProductsMapper productsMapper;
	
	public ProductsServiceImpl(ProductRepository productRepository,ProductsMapper productsMapper) {
		
		this.productRepository = productRepository;
		this.productsMapper = productsMapper;
	}

	@Override
	public Products createProduct(CreateProductRequest request) {
		
		Products product = Products.builder()
				.name(request.name())
				.description(request.description())
				.stock(request.stock())
				.price(request.price())
				.build();
		
		return productRepository.save(product);
	}

	@Override
	public List<ProductListResponse> getAllProducts() {
		
		List<Products> products = productRepository.findAll();
		
		List<ProductListResponse> productReponseList = products.stream().map(product -> productsMapper.ProductEntityToResponse(product))
				.collect(Collectors.toList());
		return productReponseList;
	}

	@Override
	public void deleteProductById(UUID product_id) {
		
		if(!productRepository.existsById(product_id)) {
			throw new ProductNotFoundException("The product not found");
		}
		
		productRepository.deleteById(product_id);
		
	}

	@Override
	public ProductUpdateResponse updateProductById(UUID id, UpdateProductRequest request) {
		
		if(!productRepository.existsById(id)) {
			throw new ProductNotFoundException("The product is not found,so update process is not performed");
		}
		
		Products product = productRepository.getReferenceById(id);
		
		product.setName(request.name());
		product.setDescription(request.description());
		product.setStock(request.stock());
		product.setPrice(request.price());
		
		productRepository.save(product);
		
		ProductUpdateResponse response = productsMapper.ProductEntityToProductUpdateResponse(product);
		
		return response;
	}

}
