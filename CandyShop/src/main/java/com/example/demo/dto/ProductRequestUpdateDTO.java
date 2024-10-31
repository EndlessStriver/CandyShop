package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestUpdateDTO {
	private String productId;
	private String productName;
	private String description;
	private String dimension;
	private double weight;
	private String subCategoryId;
	private String publisherId;
}
