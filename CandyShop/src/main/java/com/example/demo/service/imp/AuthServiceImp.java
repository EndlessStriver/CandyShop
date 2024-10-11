package com.example.demo.service.imp;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.exception.LoginFailedException;
import com.example.demo.service.AuthService;

@Service
public class AuthServiceImp implements AuthService {

	private AuthenticationManager authenticationManager;

	public AuthServiceImp(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public void login(LoginRequestDTO loginRequestDTO) {
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));
			if (!authentication.isAuthenticated())
				throw new LoginFailedException("Invalid username or password");
		} catch (BadCredentialsException e) {
			throw new LoginFailedException("Invalid username or password");
		} catch (Exception e) {
			throw e;
		}
	}

}
