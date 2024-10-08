package com.example.demo.service.imp;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.service.AuthService;

@Service
public class AuthServiceImp implements AuthService{
	
	private AuthenticationManager authenticationManager;
	
	public AuthServiceImp(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public void login(LoginRequestDTO loginRequestDTO) throws Exception {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));
		if (!authentication.isAuthenticated()) throw new Exception("Authentication failed");
	}

}
