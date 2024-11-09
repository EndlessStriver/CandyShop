package com.example.demo.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProductRequestDTO extends ProductRequestUpdateDTO {
	
	@NotNull(message = "Main image is required")
	private MultipartFile mainImage;
	
	@Min(value = 1, message = "Price must be greater than 0")
	private double price;
	
}
