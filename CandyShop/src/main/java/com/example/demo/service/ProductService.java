package com.example.demo.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.PagedResponseDTO;
import com.example.demo.dto.PriceHistoryRequestDTO;
import com.example.demo.dto.ProductRequestDTO;
import com.example.demo.dto.ProductRequestUpdateDTO;
import com.example.demo.dto.ProductResponseDTO;
import com.example.demo.model.PriceHistory;

public interface ProductService {

	public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) throws IOException, Exception;

	public ProductResponseDTO getProduct(String id);

	public ProductResponseDTO updateProduct(String id, ProductRequestUpdateDTO productRequestUpdateDTO);

	public void deleteProduct(String id) throws Exception;

	public PagedResponseDTO<ProductResponseDTO> getProducts(int page, int limit, String sortField, String sortOrder);

	public ProductResponseDTO updateProductMainImage(String id, MultipartFile mainImage) throws IOException, Exception;

	public ProductResponseDTO updateProductImages(String id, MultipartFile[] images) throws IOException, Exception;

	public PriceHistory createPriceHistory(String productId, PriceHistoryRequestDTO priceHistoryRequestDTO);

	public PagedResponseDTO<PriceHistory> getPriceHistoriesByProductId(String productId, int page, int size,
			String sortField, String sortOder);

	public PriceHistory getCurrentPriceProductByProductId(String productId);

}
