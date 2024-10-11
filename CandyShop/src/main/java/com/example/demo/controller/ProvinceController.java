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
import com.example.demo.dto.PagedResponseDTO;
import com.example.demo.dto.ProvinceRequestDTO;
import com.example.demo.exception.ResourceConflictException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Province;
import com.example.demo.service.ProvinceService;

@RestController
@RequestMapping("/api/provinces")
public class ProvinceController {

	private ProvinceService provinceService;

	public ProvinceController(ProvinceService provinceService) {
		this.provinceService = provinceService;
	}

	@GetMapping
	public ResponseEntity<ApiResponseDTO<PagedResponseDTO<Province>>> getProvinces(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit,
			@RequestParam(defaultValue = "provinceName") String sortField,
			@RequestParam(defaultValue = "asc") String sortOder) throws Exception {
		PagedResponseDTO<Province> pagedResponseDTO = provinceService.getProvinces(page, limit, sortField, sortOder);
		return ResponseEntity.ok(new ApiResponseDTO<PagedResponseDTO<Province>>("Get provinces success!",
				HttpStatus.OK.value(), pagedResponseDTO));
	}

	@GetMapping("/{idProvince}")
	public ResponseEntity<ApiResponseDTO<Province>> getProvince(@PathVariable String idProvince)
			throws ResourceNotFoundException, Exception {
		Province province = provinceService.getProvince(idProvince);
		return ResponseEntity
				.ok(new ApiResponseDTO<Province>("Get province success!", HttpStatus.OK.value(), province));
	}

	@PostMapping
	public ResponseEntity<ApiResponseDTO<Province>> createProvince(@RequestBody ProvinceRequestDTO provinceRequestDTO)
			throws ResourceConflictException, Exception {
		Province province = new Province();
		province.setProvinceName(provinceRequestDTO.getProvinceName());

		Province myProvince = provinceService.createProvince(province);

		ApiResponseDTO<Province> apiResponseDTO = new ApiResponseDTO<Province>("Create province success!",
				HttpStatus.OK.value(), myProvince);
		return new ResponseEntity<ApiResponseDTO<Province>>(apiResponseDTO, HttpStatus.OK);
	}

	@PutMapping("/{idProvince}")
	public ResponseEntity<ApiResponseDTO<Province>> updateProvince(@PathVariable String idProvince,
			@RequestBody ProvinceRequestDTO provinceRequestDTO)
			throws ResourceNotFoundException, ResourceConflictException, Exception {
		Province province = provinceService.getProvince(idProvince);
		if (provinceRequestDTO.getProvinceName().equals(province.getProvinceName()))
			throw new ResourceConflictException("No changes were made to the province name");
		province.setProvinceName(provinceRequestDTO.getProvinceName());
		Province myProvince = provinceService.updateProvince(province);
		return ResponseEntity
				.ok(new ApiResponseDTO<Province>("Update province success!", HttpStatus.OK.value(), myProvince));
	}

	@DeleteMapping("/{idProvince}")
	public ResponseEntity<ApiResponseDTO<Void>> deleteProvince(@PathVariable String idProvince)
			throws ResourceNotFoundException, Exception {
		provinceService.deleteProvince(idProvince);
		return ResponseEntity.ok(new ApiResponseDTO<Void>("Delete province success!", HttpStatus.OK.value(), null));
	}

}
