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
import com.example.demo.dto.DistrictRequestDTO;
import com.example.demo.exception.ResourceConflictException;
import com.example.demo.model.District;
import com.example.demo.service.DistrictService;

@RestController
@RequestMapping("/api/districts")
public class DistrictController {

	private DistrictService districtService;

	public DistrictController(DistrictService districtService) {
		this.districtService = districtService;
	}
	
	@GetMapping("/{idDistrict}")
	public ResponseEntity<?> getDistrict(@PathVariable String idDistrict) throws Exception {
		District district = districtService.getDistrict(idDistrict);
		ApiResponseDTO<District> response = new ApiResponseDTO<>("Get district successfully", HttpStatus.OK.value(), district);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/{idProvince}")
	public ResponseEntity<?> createDistrict(@PathVariable String idProvince, @RequestBody DistrictRequestDTO districtRequestDTO)
			throws ResourceConflictException, Exception {
		System.out.println("idProvince: " + idProvince);
		District district = districtService.createDistrict(idProvince, districtRequestDTO);
		ApiResponseDTO<District> response = new ApiResponseDTO<>("Create district successfully", HttpStatus.CREATED.value(), district);
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/{idDistrict}")
	public ResponseEntity<?> updateDistrict(@PathVariable String idDistrict, @RequestBody DistrictRequestDTO districtRequestDTO)
			throws Exception {
		District district = districtService.updateDistrict(idDistrict, districtRequestDTO);
		ApiResponseDTO<District> response = new ApiResponseDTO<>("Update district successfully", HttpStatus.OK.value(), district);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/{idDistrict}")
	public ResponseEntity<?> deleteDistrict(@PathVariable String idDistrict) throws Exception {
		 districtService.deleteDistrict(idDistrict);
		 return ResponseEntity.ok(new ApiResponseDTO<>("Delete district successfully", HttpStatus.OK.value(), null));
	}
}
