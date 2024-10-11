package com.example.demo.service;

import com.example.demo.dto.PagedResponseDTO;
import com.example.demo.exception.ResourceConflictException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Province;

public interface ProvinceService {
	
	Province createProvince(Province province) throws Exception, ResourceConflictException;
	
	Province updateProvince(Province province) throws Exception;
	
	void deleteProvince(String id) throws Exception, ResourceNotFoundException;
	
	Province getProvince(String id) throws Exception, ResourceNotFoundException;
	
	PagedResponseDTO<Province> getProvinces(int page, int limit, String sortField, String sortOder) throws Exception;
	
}
