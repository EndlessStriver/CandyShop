package com.example.demo.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.ApiResponseDTO;
import com.example.demo.dto.PagedResponseDTO;
import com.example.demo.dto.ProductRequestDTO;
import com.example.demo.dto.ProductRequestUpdateDTO;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	private ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getProductById(@PathVariable String id) {
		Product product = productService.getProduct(id);
		ApiResponseDTO<Product> apiResponseDTO = new ApiResponseDTO<>("Get product success", HttpStatus.OK.value(),
				product);
		return ResponseEntity.status(HttpStatus.OK).body(apiResponseDTO);
	}

	@GetMapping
	public ResponseEntity<?> getAllProduct(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "productName") String sortField,
			@RequestParam(defaultValue = "asc") String sortOder) {
		PagedResponseDTO<Product> pagedResponseDTO = productService.getProducts(page, limit, sortField, sortOder);
		ApiResponseDTO<PagedResponseDTO<Product>> apiResponseDTO = new ApiResponseDTO<>("Get all product success",
				HttpStatus.OK.value(), pagedResponseDTO);
		return ResponseEntity.status(HttpStatus.OK).body(apiResponseDTO);
	}

	@PostMapping
	public ResponseEntity<?> createProduct(ProductRequestDTO productRequestDTO) throws IOException, Exception {
		Product product = productService.createProduct(productRequestDTO);
		ApiResponseDTO<Product> apiResponseDTO = new ApiResponseDTO<>("Create product success",
				HttpStatus.CREATED.value(), product);
		return ResponseEntity.status(HttpStatus.CREATED).body(apiResponseDTO);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteProduct(String id) {
		productService.deleteProduct(id);
		ApiResponseDTO<Product> apiResponseDTO = new ApiResponseDTO<>("Delete product success", HttpStatus.OK.value(),
				null);
		return ResponseEntity.status(HttpStatus.OK).body(apiResponseDTO);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable String id, ProductRequestUpdateDTO productRequestUpdateDTO) {
		Product product = productService.updateProduct(id, productRequestUpdateDTO);
		ApiResponseDTO<Product> apiResponseDTO = new ApiResponseDTO<>("Update product success", HttpStatus.OK.value(),
				product);
		return ResponseEntity.status(HttpStatus.OK).body(apiResponseDTO);
	}
	
	@PatchMapping("/{id}/main-image")
	public ResponseEntity<?> updateProductMainImage(@PathVariable String id, MultipartFile mainImage)
			throws IOException, Exception {
		Product product = productService.updateProductMainImage(id, mainImage);
		ApiResponseDTO<Product> apiResponseDTO = new ApiResponseDTO<>("Update product main image success",
				HttpStatus.OK.value(), product);
		return ResponseEntity.status(HttpStatus.OK).body(apiResponseDTO);
	}
	
	@PatchMapping("/{id}/images")
	public ResponseEntity<?> updateProductImages(@PathVariable String id, MultipartFile[] images)
			throws IOException, Exception {
		Product product = productService.updateProductImages(id, images);
		ApiResponseDTO<Product> apiResponseDTO = new ApiResponseDTO<>("Update product images success",
				HttpStatus.OK.value(), product);
		return ResponseEntity.status(HttpStatus.OK).body(apiResponseDTO);
	}
}
