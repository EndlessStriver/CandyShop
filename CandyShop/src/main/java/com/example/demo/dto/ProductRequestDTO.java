package com.example.demo.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {
	private String productName;
	private String description;
	private String dimension;
	private double weight;
	private MultipartFile mainImage;
	private String subCategoryId;
	private String publisherId;
	private double price;
}
