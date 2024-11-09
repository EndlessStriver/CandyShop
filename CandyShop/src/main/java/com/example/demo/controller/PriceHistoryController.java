package com.example.demo.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

import jakarta.validation.Valid;

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
			@Valid @RequestBody PriceHistoryRequestDTO priceHistoryRequestDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			Map<String, Object> errors = new LinkedHashMap<String, Object>();
			bindingResult.getFieldErrors().forEach(error -> {
				errors.put(error.getField(), error.getDefaultMessage());
			});
			ApiResponseDTO<Map<String, Object>> response = new ApiResponseDTO<>("Validation failed", HttpStatus.BAD_REQUEST.value(), errors);
			return ResponseEntity.badRequest().body(response);
		}
		PriceHistory priceHistory = priceHistoryService.updatePriceHistory(priceHistoryId, priceHistoryRequestDTO);
		ApiResponseDTO<PriceHistory> response = new ApiResponseDTO<>("Price history updated", HttpStatus.OK.value(), priceHistory);
		return ResponseEntity.ok(response);
	}

}
