package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.District;

@Repository
public interface DistrictRepository extends JpaRepository<District, String>{

	boolean existsByDistrictName(String districtName);
	
	@Query("SELECT d FROM District d WHERE d.province.provinceId = :provinceId")
	Page<District> findByProvinceProvinceId(@Param("provinceId") String provinceId, Pageable pageable);
	
	Optional<District> findByDistrictIdAndProvinceProvinceId(String districtId, String provinceId);
	
}
