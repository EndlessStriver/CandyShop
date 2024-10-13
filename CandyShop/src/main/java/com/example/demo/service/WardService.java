package com.example.demo.service;

import com.example.demo.dto.PagedResponseDTO;
import com.example.demo.exception.ResourceConflictException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Province;
import com.example.demo.model.Ward;

public interface WardService {
	
	Ward createWard(Ward ward) throws Exception, ResourceConflictException;

	Ward updateWard(Ward ward) throws Exception;

	void deleteWard(String id) throws Exception, ResourceNotFoundException;

	Province getWard(String id) throws Exception, ResourceNotFoundException;

	PagedResponseDTO<Ward> getWards(int page, int limit, String sortField, String sortOder) throws Exception;
	
}
