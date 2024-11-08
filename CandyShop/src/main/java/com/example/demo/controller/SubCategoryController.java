package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ApiResponseDTO;
import com.example.demo.dto.SubCategoryRequestDTO;
import com.example.demo.model.SubCategory;
import com.example.demo.service.SubCategoryService;

@RestController
@RequestMapping("/api/subcategories")
public class SubCategoryController {

	private SubCategoryService subCategoryService;

	public SubCategoryController(SubCategoryService subCategoryService) {
		this.subCategoryService = subCategoryService;
	}

	@PostMapping("/{categoryId}")
	public ResponseEntity<?> createSubCategory(@PathVariable String categoryId,
			@RequestBody SubCategoryRequestDTO categoryRequestDTO) {
		SubCategory subCategory = subCategoryService.createSubCategory(categoryId, categoryRequestDTO);
		ApiResponseDTO<SubCategory> response = new ApiResponseDTO<>("SubCategory created successfully",
				HttpStatus.CREATED.value(), subCategory);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PatchMapping("/{subCategoryId}")
	public ResponseEntity<?> updateSubCategory(@PathVariable String subCategoryId,
			@RequestBody SubCategoryRequestDTO subCategoryRequestDTO) {
		SubCategory subCategory = subCategoryService.updateSubCategory(subCategoryId, subCategoryRequestDTO);
		ApiResponseDTO<SubCategory> response = new ApiResponseDTO<>("SubCategory updated successfully",
				HttpStatus.OK.value(), subCategory);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@DeleteMapping("/{subCategoryId}")
	public ResponseEntity<?> deleteSubCategory(@PathVariable String subCategoryId) {
		subCategoryService.deleteSubCategory(subCategoryId);
		ApiResponseDTO<String> response = new ApiResponseDTO<>("SubCategory deleted successfully",
				HttpStatus.OK.value(), null);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/{subCategoryId}")
	public ResponseEntity<?> getSubCategory(@PathVariable String subCategoryId) {
		SubCategory subCategory = subCategoryService.getSubCategory(subCategoryId);
		ApiResponseDTO<SubCategory> response = new ApiResponseDTO<>("SubCategory retrieved successfully",
				HttpStatus.OK.value(), subCategory);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
