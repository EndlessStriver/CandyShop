package com.example.demo.controller;

import java.util.List;

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
import com.example.demo.dto.CategoryRequestDTO;
import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	private CategoryService categoryService;
	
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping
	public ResponseEntity<?> getCategories() {
		List<Category> categories = categoryService.getAllCategories();
		ApiResponseDTO<List<Category>> response = new ApiResponseDTO<>("Categories retrieved successfully", HttpStatus.OK.value(), categories);
        return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<?> getCategory(@PathVariable String categoryId) {
        Category category = categoryService.getCategory(categoryId);
        ApiResponseDTO<Category> response = new ApiResponseDTO<>("Category retrieved successfully", HttpStatus.OK.value(), category);
        return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PatchMapping("/{categoryId}")
	public ResponseEntity<?> updateCategory(@PathVariable String categoryId, @RequestBody CategoryRequestDTO categoryName) {
		Category category = categoryService.updateCategory(categoryId, categoryName);
		ApiResponseDTO<Category> response = new ApiResponseDTO<>("Category updated successfully", HttpStatus.OK.value(),
				category);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping
	public ResponseEntity<?> createCategory(@RequestBody CategoryRequestDTO categoryName) {
		Category category = categoryService.createCategory(categoryName);
		ApiResponseDTO<Category> response = new ApiResponseDTO<>("Category created successfully", HttpStatus.CREATED.value(), category);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<?> deleteCategory(@PathVariable String categoryId) {
		categoryService.deleteCategory(categoryId);
		ApiResponseDTO<Category> response = new ApiResponseDTO<>("Category deleted successfully", HttpStatus.OK.value(),
				null);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
}
