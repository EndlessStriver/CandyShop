package com.example.demo.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ImageService;

@RestController
@RequestMapping("/api/images")
public class ImageController {
	
	private ImageService imageService;
	
	public ImageController(ImageService imageService) {
		this.imageService = imageService;
	}
	
	@DeleteMapping("/{id}")
	public void deleteImage(String imageId) throws Exception {
		imageService.deleteImage(imageId);
	}
	
}
