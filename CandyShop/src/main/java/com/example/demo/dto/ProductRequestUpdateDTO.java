package com.example.demo.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestUpdateDTO {
	
	@NotBlank(message = "Product name is required")
	@Size(min = 2, max = 50, message = "Product name must be between 2 and 50 characters")
	private String productName;
	
	private String description;
	
	@NotBlank(message = "Dimension is required")
	@Pattern(regexp = "^[0-9]+x[0-9]+x[0-9]+$", message = "Dimension must be in the format of 'x' separated numbers")
	private String dimension;
	
	@DecimalMin(value = "0.1", message = "Weight must be greater than 0")
	private double weight;
	
	@NotBlank(message = "SubCategory ID is required")
	private String subCategoryId;
	
	@NotBlank(message = "Publisher ID is required")
	private String publisherId;
	
}
