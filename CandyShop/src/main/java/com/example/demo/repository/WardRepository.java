package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Ward;

@Repository
public interface WardRepository extends JpaRepository<Ward, String>{
	
	boolean existsByWardName(String wardName);
	
}
