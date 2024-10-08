package com.example.demo.service;

import com.example.demo.dto.LoginRequestDTO;

public interface AuthService {
	
	public void login(LoginRequestDTO loginRequestDTO) throws Exception;
	
}
