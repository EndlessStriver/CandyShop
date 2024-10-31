package com.example.demo.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.PagedResponseDTO;
import com.example.demo.dto.ProductRequestDTO;
import com.example.demo.dto.ProductRequestUpdateDTO;
import com.example.demo.model.Product;

public interface ProductService {
	
	public Product createProduct(ProductRequestDTO productRequestDTO) throws IOException, Exception;

	public Product getProduct(String id);

	public Product updateProduct(ProductRequestUpdateDTO productRequestUpdateDTO);

	public void deleteProduct(String id);

	public PagedResponseDTO<Product> getProducts(int page, int limit, String sortField, String sortOrder);
	
	public Product updateProductMainImage(String id, MultipartFile mainImage) throws IOException, Exception;
	
	public Product updateProductImages(String id, MultipartFile[] images) throws IOException, Exception;
	
}
