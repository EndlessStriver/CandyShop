package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ApiResponseDTO;
import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.dto.SendOtpRequest;
import com.example.demo.exception.ResourceConflictException;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.model.User;
import com.example.demo.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) throws Exception, UnauthorizedException {
		authService.login(loginRequestDTO);
		return ResponseEntity.ok(new ApiResponseDTO<>("Login Success!", HttpStatus.OK.value(), null));
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequestDTO registerRequestDTO) throws Exception, ResourceConflictException {
        User user = authService.register(registerRequestDTO);
        return ResponseEntity.ok(new ApiResponseDTO<User>("Register Success!", HttpStatus.OK.value(), user));
    }
	
	@PostMapping("/otp")
	public ResponseEntity<?> sendOTP(@RequestBody SendOtpRequest email) throws Exception {
		authService.sendOTP(email);
		return ResponseEntity.ok(new ApiResponseDTO<>("OTP sent!", HttpStatus.OK.value(), null));
	}

}
