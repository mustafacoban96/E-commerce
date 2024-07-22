package com.shepherd.E_commerce.mappers;

import com.shepherd.E_commerce.dto.response.IndividualCartItemResponse;
import org.springframework.stereotype.Component;

import com.shepherd.E_commerce.dto.response.GetProductByIdResponse;
import com.shepherd.E_commerce.dto.response.ProductListResponse;
import com.shepherd.E_commerce.dto.response.ProductUpdateResponse;
import com.shepherd.E_commerce.models.Products;


@Component
public class ProductsMapper {
	
	//list
	public ProductListResponse ProductEntityToResponse(Products product) {
		if(product == null) {
			return null;
		}
		
		return new ProductListResponse(
				product.getId(),
				product.getName(), 
				product.getDescription(),
				product.getStock(),
				product.getPrice());
	}
	
	//update
	public ProductUpdateResponse ProductEntityToProductUpdateResponse(Products product) {
		return new ProductUpdateResponse(product.getId(),product.getName(), product.getDescription(),product.getStock(),product.getPrice());
	}
	
	//get individual
	public GetProductByIdResponse ProductEntityToResponseById(Products product) {
		if(product == null) {
			return null;
		}
		
		return new GetProductByIdResponse(product.getId(),product.getName(), product.getDescription(),product.getStock(),product.getPrice());
	}


	public IndividualCartItemResponse productToIndividualCartItem(Products product){
		if(product == null) {
			return null;
		}

		IndividualCartItemResponse individualCartItemResponse = IndividualCartItemResponse.builder()
				.id(product.getId())
				.name(product.getName())
				.description(product.getDescription())
				.price(product.getPrice())
				.stock(product.getStock())
				.build();

		return individualCartItemResponse;
	}

}
