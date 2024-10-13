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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ApiResponseDTO;
import com.example.demo.dto.DistrictRequestDTO;
import com.example.demo.dto.PagedResponseDTO;
import com.example.demo.dto.ProvinceRequestDTO;
import com.example.demo.exception.ResourceConflictException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.District;
import com.example.demo.model.Province;
import com.example.demo.service.DistrictService;
import com.example.demo.service.ProvinceService;

@RestController
@RequestMapping("/api/provinces")
public class LocationController {

	private ProvinceService provinceService;
	private DistrictService districtService;

	public LocationController(ProvinceService provinceService, DistrictService districtService) {
		this.provinceService = provinceService;
		this.districtService = districtService;
	}
	
//	PROVINCE
	@GetMapping
	public ResponseEntity<?> getProvinces(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit,
			@RequestParam(defaultValue = "provinceName") String sortField,
			@RequestParam(defaultValue = "asc") String sortOder) throws Exception {
		PagedResponseDTO<Province> pagedResponseDTO = provinceService.getProvinces(page, limit, sortField, sortOder);
		return ResponseEntity.ok(new ApiResponseDTO<PagedResponseDTO<Province>>("Get provinces success!",
				HttpStatus.OK.value(), pagedResponseDTO));
	}

	@GetMapping("/{idProvince}")
	public ResponseEntity<?> getProvince(@PathVariable String idProvince)
			throws ResourceNotFoundException, Exception {
		Province province = provinceService.getProvince(idProvince);
		return ResponseEntity
				.ok(new ApiResponseDTO<Province>("Get province success!", HttpStatus.OK.value(), province));
	}

	@PostMapping
	public ResponseEntity<?> createProvince(@RequestBody ProvinceRequestDTO provinceRequestDTO)
			throws ResourceConflictException, Exception {

		Province myProvince = provinceService.createProvince(provinceRequestDTO);

		ApiResponseDTO<Province> apiResponseDTO = new ApiResponseDTO<Province>("Create province success!",
				HttpStatus.OK.value(), myProvince);
		return new ResponseEntity<ApiResponseDTO<Province>>(apiResponseDTO, HttpStatus.OK);
	}

	@PutMapping("/{idProvince}")
	public ResponseEntity<?> updateProvince(@PathVariable String idProvince,
			@RequestBody ProvinceRequestDTO provinceRequestDTO)
			throws ResourceNotFoundException, ResourceConflictException, Exception {
		Province myProvince = provinceService.updateProvince(idProvince, provinceRequestDTO);
		return ResponseEntity
				.ok(new ApiResponseDTO<Province>("Update province success!", HttpStatus.OK.value(), myProvince));
	}

	@DeleteMapping("/{idProvince}")
	public ResponseEntity<?> deleteProvince(@PathVariable String idProvince)
			throws ResourceNotFoundException, Exception {
		provinceService.deleteProvince(idProvince);
		return ResponseEntity.ok(new ApiResponseDTO<Void>("Delete province success!", HttpStatus.OK.value(), null));
	}
	
//	DISTRICT
	@GetMapping("/{idProvince}/districts")
	public ResponseEntity<?> getDistricts(@PathVariable String idProvince, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "districtName") String sortField,
			@RequestParam(defaultValue = "asc") String sortOder) throws Exception {
		PagedResponseDTO<District> districts = districtService.getDistrict(idProvince, page, limit, sortField, sortOder);
		ApiResponseDTO<PagedResponseDTO<District>> response = new ApiResponseDTO<>("Get districts successfully",
				HttpStatus.OK.value(), districts);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{idProvince}/districts/{idDistrict}")
	public ResponseEntity<?> getDistrict(@PathVariable String idProvince, @PathVariable String idDistrict) throws ResourceNotFoundException, Exception {
		District district = districtService.getDistrict(idProvince, idDistrict);
		return ResponseEntity
				.ok(new ApiResponseDTO<District>("Get district successfully", HttpStatus.OK.value(), district));
	}
	
	@PostMapping("/{idProvince}/districts")
	public ResponseEntity<?> createDistrict(@PathVariable String idProvince, @RequestBody DistrictRequestDTO districtRequestDTO)
			throws ResourceConflictException, Exception {
		District myDistrict = districtService.createDistrict(idProvince, districtRequestDTO);
		return ResponseEntity
				.ok(new ApiResponseDTO<District>("Create district successfully", HttpStatus.OK.value(), myDistrict));
	}
	
	@PutMapping("/{idProvince}/districts/{idDistrict}")
	public ResponseEntity<?> updateDistrict(@PathVariable String idProvince, @PathVariable String idDistrict,
			@RequestBody DistrictRequestDTO districtRequestDTO) throws ResourceNotFoundException, Exception {
		District myDistrict = districtService.updateDistrict(idProvince, idDistrict, districtRequestDTO);
		return ResponseEntity
				.ok(new ApiResponseDTO<District>("Update district successfully", HttpStatus.OK.value(), myDistrict));
	}
	
	@DeleteMapping("/{idProvince}/districts/{idDistrict}")
	public ResponseEntity<?> deleteDistrict(@PathVariable String idProvince, @PathVariable String idDistrict)
			throws ResourceNotFoundException, Exception {
		districtService.deleteDistrict(idProvince, idDistrict);
		return ResponseEntity.ok(new ApiResponseDTO<Void>("Delete district successfully", HttpStatus.OK.value(), null));
	}
	

}
