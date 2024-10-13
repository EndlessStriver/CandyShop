package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ApiResponseDTO;
import com.example.demo.dto.WardRequestDTO;
import com.example.demo.model.Ward;
import com.example.demo.service.WardService;

@RestController
@RequestMapping("/api/wards")
public class WardController {
	
	private WardService wardService;
	
	public WardController(WardService wardService) {
		this.wardService = wardService;
	}
	
	@GetMapping("/{wardId}")
	public ResponseEntity<?> getWard(@PathVariable String wardId) throws Exception {
		Ward ward = wardService.getWard(wardId);
		ApiResponseDTO<Ward> response = new ApiResponseDTO<>("Get ward successfully", HttpStatus.OK.value(), ward);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/{districtId}")
	public ResponseEntity<?> createWard(@PathVariable String districtId, @RequestBody WardRequestDTO wardRequestDTO) throws Exception {
		Ward ward = wardService.createWard(districtId, wardRequestDTO);
        ApiResponseDTO<Ward> response = new ApiResponseDTO<>("Create ward successfully", HttpStatus.CREATED.value(), ward);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
	
	@PutMapping("/{wardId}")
	public ResponseEntity<?> updateWard(@PathVariable String wardId, @RequestBody WardRequestDTO wardRequestDTO)
			throws Exception {
		Ward ward = wardService.updateWard(wardId, wardRequestDTO);
		ApiResponseDTO<Ward> response = new ApiResponseDTO<>("Update ward successfully", HttpStatus.OK.value(), ward);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/{wardId}")
	public ResponseEntity<?> deleteWard(@PathVariable String wardId) throws Exception {
		wardService.deleteWard(wardId);
		ApiResponseDTO<String> response = new ApiResponseDTO<>("Delete ward successfully", HttpStatus.OK.value(),
				"Deleted");
		return ResponseEntity.ok(response);
	}
}
