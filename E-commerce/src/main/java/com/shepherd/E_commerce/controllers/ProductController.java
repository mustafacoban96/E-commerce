package com.shepherd.E_commerce.controllers;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.shepherd.E_commerce.dto.requests.CreateProductRequest;
import com.shepherd.E_commerce.dto.requests.UpdateProductRequest;
import com.shepherd.E_commerce.dto.response.GetProductByIdResponse;
import com.shepherd.E_commerce.dto.response.ProductListResponseV2;
import com.shepherd.E_commerce.dto.response.ProductUpdateResponse;
import com.shepherd.E_commerce.service.ProductService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

	private final ProductService productService;
	
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	//read
		//@GetMapping("/")
		/*@GetMapping("/")
		public ResponseEntity<List<ProductListResponse>> getAllProductsAsList(){
			
			List<ProductListResponse> listAllProducts = productServcie.getAllProducts();
			
			return new ResponseEntity<>(listAllProducts,HttpStatus.OK);
			
		}
		}*/

		//@GetMapping("/e" )
		@GetMapping("/product-list")
		public ResponseEntity <ProductListResponseV2> getAllProductsAsListV2(
				@RequestParam(value="pageNo",defaultValue = "0",required = false) int pageNo,
				@RequestParam(value="pageSize",defaultValue = "12",required = false) int pageSize
				){
			return new ResponseEntity<>(productService.getAllProductV2(pageNo,pageSize), HttpStatus.OK);
		}
	
	//create
	@PostMapping("/create-product")
	public ResponseEntity<String> createProduct(@Valid @RequestBody CreateProductRequest request){
		System.out.println(request);
		productService.createProduct(request);
		return new ResponseEntity<>("Product is added successfully",HttpStatus.CREATED);
	}
	
	//getById
	@GetMapping("/product/{product_id}")
	public ResponseEntity<GetProductByIdResponse> getProductById(@PathVariable("product_id") UUID id) {
		GetProductByIdResponse response = productService.getProductById(id);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	
	//delete
	@DeleteMapping("/delete-product/{product_id}")
	public ResponseEntity<String> deleteProductById(@PathVariable("product_id") UUID id){
		productService.deleteProductById(id);
		return new ResponseEntity<>("the product is deleted successfully",HttpStatus.OK);
	}
	
	//update
	@PutMapping("/update-product/{product_id}")
	public ResponseEntity<ProductUpdateResponse> updatePrdouctById(@PathVariable("product_id") UUID id,
			@Valid @RequestBody UpdateProductRequest request){
		
		ProductUpdateResponse response =  productService.updateProductById(id, request);
		return new ResponseEntity<>(response,HttpStatus.OK);
		
	}
}
