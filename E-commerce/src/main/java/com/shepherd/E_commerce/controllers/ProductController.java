package com.shepherd.E_commerce.controllers;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shepherd.E_commerce.dto.requests.CreateProductRequest;
import com.shepherd.E_commerce.dto.requests.UpdateProductRequest;
import com.shepherd.E_commerce.dto.response.GetProductByIdResponse;
import com.shepherd.E_commerce.dto.response.ProductListResponse;
import com.shepherd.E_commerce.dto.response.ProductUpdateResponse;
import com.shepherd.E_commerce.service.ProductServcie;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	private final ProductServcie productServcie;
	
	public ProductController(ProductServcie productServcie) {
		this.productServcie = productServcie;
	}
	
	
	//create
	@PostMapping("/create-product")
	public ResponseEntity<String> createProduct(@Valid @RequestBody CreateProductRequest request){
		productServcie.createProduct(request);
		return new ResponseEntity<>("Prodcut is added successfully",HttpStatus.CREATED);
	}
	
	//getById
	@GetMapping("/product/{product_id}")
	public ResponseEntity<GetProductByIdResponse> getProductById(@PathVariable("product_id") UUID id) {
		GetProductByIdResponse response = productServcie.getProductById(id);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	//read
	@GetMapping("/product-list")
	public ResponseEntity<List<ProductListResponse>> getAllProductsAsList(){
		
		List<ProductListResponse> listAllProducts = productServcie.getAllProducts();
		
		return new ResponseEntity<>(listAllProducts,HttpStatus.OK);
		
	}
	//delete
	@DeleteMapping("/delete-product/{product_id}")
	public ResponseEntity<String> deleteProductById(@PathVariable("product_id") UUID id){
		productServcie.deleteProductById(id);
		return new ResponseEntity<>("the product is deleted successfully",HttpStatus.OK);
	}
	
	//update
	@PutMapping("/update-product/{product_id}")
	public ResponseEntity<ProductUpdateResponse> updatePrdouctById(@PathVariable("product_id") UUID id,
			@Valid @RequestBody UpdateProductRequest request){
		
		ProductUpdateResponse response =  productServcie.updateProductById(id, request);
		return new ResponseEntity<>(response,HttpStatus.OK);
		
	}

}
