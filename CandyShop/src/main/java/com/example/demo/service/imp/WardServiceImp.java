package com.example.demo.service.imp;

import org.springframework.stereotype.Service;

import com.example.demo.dto.PagedResponseDTO;
import com.example.demo.exception.ResourceConflictException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Province;
import com.example.demo.model.Ward;
import com.example.demo.repository.WardRepository;
import com.example.demo.service.WardService;

@Service
public class WardServiceImp implements WardService {
	
	private WardRepository wardRepository;
	
	public WardServiceImp(WardRepository wardRepository) {
		this.wardRepository = wardRepository;
	}

	@Override
	public Ward createWard(Ward ward) throws Exception, ResourceConflictException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ward updateWard(Ward ward) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteWard(String id) throws Exception, ResourceNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Province getWard(String id) throws Exception, ResourceNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PagedResponseDTO<Ward> getWards(int page, int limit, String sortField, String sortOder) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
