package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ApiResponseDTO;
import com.example.demo.dto.PriceHistoryRequestDTO;
import com.example.demo.model.PriceHistory;
import com.example.demo.service.PriceHistoryService;

@RestController
@RequestMapping("/api/price-histories")
public class PriceHistoryController {
	
	private PriceHistoryService priceHistoryService;
	
	public PriceHistoryController(PriceHistoryService priceHistoryService) {
		this.priceHistoryService = priceHistoryService;
	}
	
	@GetMapping("/{priceHistoryId}")
	public ResponseEntity<?> getPriceHistory(@PathVariable String priceHistoryId) {
		PriceHistory priceHistory = priceHistoryService.getPriceHistory(priceHistoryId);
		ApiResponseDTO<PriceHistory> response = new ApiResponseDTO<>("Price history retrieved", HttpStatus.OK.value(),
				priceHistory);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/{priceHistoryId}")
	public ResponseEntity<?> deletePriceHistory(@PathVariable String priceHistoryId) {
		priceHistoryService.deletePriceHistory(priceHistoryId);
		ApiResponseDTO<String> response = new ApiResponseDTO<>("Price history deleted", HttpStatus.OK.value(), null);
		return ResponseEntity.ok(response);
	}
	
	@PatchMapping("/{priceHistoryId}")
	public ResponseEntity<?> updatePriceHistory(@PathVariable String priceHistoryId,
			@RequestBody PriceHistoryRequestDTO priceHistoryRequestDTO) {
		PriceHistory priceHistory = priceHistoryService.updatePriceHistory(priceHistoryId, priceHistoryRequestDTO);
		ApiResponseDTO<PriceHistory> response = new ApiResponseDTO<>("Price history updated", HttpStatus.OK.value(), priceHistory);
		return ResponseEntity.ok(response);
	}

}
