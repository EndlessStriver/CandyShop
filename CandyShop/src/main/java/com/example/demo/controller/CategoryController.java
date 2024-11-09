package com.example.demo.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ApiResponseDTO;
import com.example.demo.dto.ApiResponseErrorDTO;
import com.example.demo.dto.CategoryRequestDTO;
import com.example.demo.model.Category;
import com.example.demo.model.SubCategory;
import com.example.demo.service.CategoryService;

import jakarta.validation.Valid;

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
	
	@GetMapping("/{categoryId}/subcategories")
	public ResponseEntity<?> getSubcategoriesByCategoryId(@PathVariable String categoryId) {
		Category category = categoryService.getCategory(categoryId);
		List<SubCategory> categories = category.getSubCategories();
		ApiResponseDTO<List<SubCategory>> response = new ApiResponseDTO<>("SubCategories retrieved successfully", HttpStatus.OK.value(), categories);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PatchMapping("/{categoryId}")
	public ResponseEntity<?> updateCategory(@PathVariable String categoryId, @Valid @RequestBody CategoryRequestDTO categoryName, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			Map<String, Object> errors = new LinkedHashMap<String, Object>();
			bindingResult.getFieldErrors().stream().forEach(result -> {
				errors.put(result.getField(), result.getDefaultMessage());
			});
			ApiResponseErrorDTO error = new ApiResponseErrorDTO("Update Category Failed!" , HttpStatus.BAD_REQUEST.value(), errors);
			return ResponseEntity.badRequest().body(error);
		}
		Category category = categoryService.updateCategory(categoryId, categoryName);
		ApiResponseDTO<Category> response = new ApiResponseDTO<>("Category updated successfully", HttpStatus.OK.value(),
				category);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping
	public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequestDTO categoryName, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			Map<String, Object> errors = new LinkedHashMap<String, Object>();
			bindingResult.getFieldErrors().stream().forEach(result -> {
				errors.put(result.getField(), result.getDefaultMessage());
			});
			ApiResponseErrorDTO error = new ApiResponseErrorDTO("Create Category Failed!" , HttpStatus.BAD_REQUEST.value(), errors);
			return ResponseEntity.badRequest().body(error);
		}
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
