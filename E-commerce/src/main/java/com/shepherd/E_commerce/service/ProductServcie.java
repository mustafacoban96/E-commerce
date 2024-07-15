package com.shepherd.E_commerce.service;

import java.util.List;
import java.util.UUID;

import com.shepherd.E_commerce.dto.requests.CreateProductRequest;
import com.shepherd.E_commerce.dto.requests.UpdateProductRequest;
import com.shepherd.E_commerce.dto.response.GetProductByIdResponse;
import com.shepherd.E_commerce.dto.response.ProductListResponse;
import com.shepherd.E_commerce.dto.response.ProductListResponseV2;
import com.shepherd.E_commerce.dto.response.ProductUpdateResponse;
import com.shepherd.E_commerce.models.Products;

public interface ProductServcie {
	
	public Products createProduct(CreateProductRequest request);
	public List<ProductListResponse> getAllProducts();
	public ProductListResponseV2 getAllProductV2(int PageNo,int PageSize);
	public void deleteProductById(UUID product_id);
	public ProductUpdateResponse updateProductById(UUID id,UpdateProductRequest request);
	public GetProductByIdResponse getProductById(UUID id);
	

}
