package com.example.demo.service.imp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.PagedResponseDTO;
import com.example.demo.exception.ResourceConflictException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Province;
import com.example.demo.repository.ProvinceRepository;
import com.example.demo.service.ProvinceService;

@Service
public class ProvinceServiceImp implements ProvinceService {

	private ProvinceRepository provinceRepository;

	public ProvinceServiceImp(ProvinceRepository provinceRepository) {
		this.provinceRepository = provinceRepository;
	}

	@Override
	@Transactional
	public Province createProvince(Province province) throws Exception, ResourceConflictException {
		if (provinceRepository.existsByProvinceName(province.getProvinceName()))
			throw new ResourceConflictException("Province name already exists");
		return provinceRepository.save(province);
	}

	@Override
	@Transactional
	public Province updateProvince(Province province) throws Exception {
		return provinceRepository.save(province);
	}

	@Override
	@Transactional
	public void deleteProvince(String id) throws Exception, ResourceNotFoundException {
		Province province = provinceRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Province not found"));
		provinceRepository.delete(province);
	}

	@Override
	public Province getProvince(String id) throws Exception, ResourceNotFoundException {
		return provinceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Province not found"));
	}

	@Override
	public PagedResponseDTO<Province> getProvinces(int page, int limit, String sortField, String sortOder)
			throws Exception {
		Sort sort = sortOder.equalsIgnoreCase("desc") ? Sort.by(sortField).descending()
				: Sort.by(sortField).ascending();
		Pageable pageable = PageRequest.of(page, limit, sort);
		Page<Province> pageProvince = provinceRepository.findAll(pageable);
		return new PagedResponseDTO<Province>(pageProvince.getContent(), pageProvince.getNumber(), pageProvince.getSize(),
				pageProvince.getTotalElements(), pageProvince.getTotalPages());
	}

}
