package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ApiResponseDTO;
import com.example.demo.service.ImageService;

@RestController
@RequestMapping("/api/images")
public class ImageController {
	
	private ImageService imageService;
	
	public ImageController(ImageService imageService) {
		this.imageService = imageService;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteImage(@PathVariable String id) throws Exception {
		imageService.deleteImage(id);
		ApiResponseDTO<Void> response = new ApiResponseDTO<>("Image deleted successfully", HttpStatus.OK.value(), null);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
}
