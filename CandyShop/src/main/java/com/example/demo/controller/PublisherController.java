package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ApiResponseDTO;
import com.example.demo.dto.PagedResponseDTO;
import com.example.demo.dto.PublisherRequestDTO;
import com.example.demo.model.Publisher;
import com.example.demo.service.PublisherService;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {

	private PublisherService publisherService;

	public PublisherController(PublisherService publisherService) {
		this.publisherService = publisherService;
	}

	@GetMapping
	public ResponseEntity<?> getAllPublishers(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit,
			@RequestParam(defaultValue = "publisherName") String sortField,
			@RequestParam(defaultValue = "asc") String sortOrder) {
		PagedResponseDTO<Publisher> publishers = publisherService.getAllPublishers(page, limit, sortField, sortOrder);
		ApiResponseDTO<PagedResponseDTO<Publisher>> response = new ApiResponseDTO<>("Publishers retrieved successfully",
				HttpStatus.OK.value(), publishers);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{publisherId}")
	public ResponseEntity<?> getPublisher(@PathVariable String publisherId) {
		Publisher publisher = publisherService.getPublisher(publisherId);
		ApiResponseDTO<Publisher> response = new ApiResponseDTO<>("Publisher retrieved successfully",
				HttpStatus.OK.value(), publisher);
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<?> createPublisher(@RequestBody PublisherRequestDTO publisherRequestDTO) {
		Publisher publisher = publisherService.createPublisher(publisherRequestDTO);
		ApiResponseDTO<Publisher> response = new ApiResponseDTO<>("Publisher created successfully",
				HttpStatus.CREATED.value(), publisher);
		return ResponseEntity.ok(response);
	}

	@PatchMapping("/{publisherId}")
	public ResponseEntity<?> updatePublisher(@PathVariable String publisherId,
			@RequestBody PublisherRequestDTO publisherRequestDTO) {
		Publisher publisher = publisherService.updatePublisher(publisherId, publisherRequestDTO);
		ApiResponseDTO<Publisher> response = new ApiResponseDTO<>("Publisher updated successfully",
				HttpStatus.OK.value(), publisher);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{publisherId}")
	public ResponseEntity<?> deletePublisher(@PathVariable String publisherId) {
		publisherService.deletePublisher(publisherId);
		ApiResponseDTO<Publisher> response = new ApiResponseDTO<>("Publisher deleted successfully",
				HttpStatus.OK.value(), null);
		return ResponseEntity.ok(response);
	}

}
