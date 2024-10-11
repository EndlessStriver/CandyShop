package com.example.demo.service;

import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.exception.ResourceConflictException;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.model.User;

public interface AuthService {
	
	public void login(LoginRequestDTO loginRequestDTO) throws Exception, UnauthorizedException;

	public User register(RegisterRequestDTO registerRequestDTO) throws Exception, ResourceConflictException;
	
}
