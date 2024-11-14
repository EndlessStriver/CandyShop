package com.example.demo.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ApiResponseDTO;
import com.example.demo.dto.ApiResponseErrorDTO;
import com.example.demo.dto.ApiResponseNoDataDTO;
import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.dto.SendOtpRequest;
import com.example.demo.exception.ResourceConflictException;
import com.example.demo.exception.AuthenticationException;
import com.example.demo.model.User;
import com.example.demo.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO, BindingResult bindingResult)
			throws Exception, AuthenticationException {
		logger.info("Login request with username: {}", loginRequestDTO.getUsername());
		if (bindingResult.hasErrors()) {
			Map<String, Object> errors = new LinkedHashMap<String, Object>();
			bindingResult.getFieldErrors().stream().forEach(result -> {
				errors.put(result.getField(), result.getDefaultMessage());
			});
			ApiResponseErrorDTO error = new ApiResponseErrorDTO("Login Failed!", HttpStatus.BAD_REQUEST.value(), errors);
			logger.error("Login failed with username: {}", loginRequestDTO.getUsername());
			return ResponseEntity.badRequest().body(error);
		}
		LoginResponseDTO loginResponseDTO = authService.login(loginRequestDTO);
		logger.info("Login success with username: {}", loginRequestDTO.getUsername());
		return ResponseEntity.ok(new ApiResponseDTO<>("Login Success!", HttpStatus.OK.value(), loginResponseDTO));
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO, BindingResult bindingResult)
			throws Exception, ResourceConflictException {
		logger.info("Register request with username: {}", registerRequestDTO.getUserName());
		if (bindingResult.hasErrors()) {
			Map<String, Object> errors = new LinkedHashMap<String, Object>();
			bindingResult.getFieldErrors().stream().forEach(result -> {
				errors.put(result.getField(), result.getDefaultMessage());
			});
			ApiResponseErrorDTO error = new ApiResponseErrorDTO("Register Failed!", HttpStatus.BAD_REQUEST.value(), errors);
			logger.error("Register failed with username: {}", registerRequestDTO.getUserName());
			return ResponseEntity.badRequest().body(error);
		}
		User user = authService.register(registerRequestDTO);
		logger.info("Register success with username: {}", registerRequestDTO.getUserName());
		return ResponseEntity.ok(new ApiResponseDTO<User>("Register Success!", HttpStatus.OK.value(), user));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/otp")
	public ResponseEntity<?> sendOTP(@Valid @RequestBody SendOtpRequest email, BindingResult bindingResult) throws Exception {
		logger.info("OTP request with email: {}", email.getEmail());
		if (bindingResult.hasErrors()) {
			Map<String, Object> errors = new LinkedHashMap<String, Object>();
			bindingResult.getFieldErrors().stream().forEach(result -> {
				errors.put(result.getField(), result.getDefaultMessage());
			});
			ApiResponseErrorDTO error = new ApiResponseErrorDTO("OTP Failed!", HttpStatus.BAD_REQUEST.value(), errors);
			logger.error("OTP failed with email: {}", email.getEmail());
			return ResponseEntity.badRequest().body(error);
		}
		authService.sendOTP(email);
		logger.info("OTP sent to: {}", email.getEmail());
		return ResponseEntity.ok(new ApiResponseNoDataDTO("OTP sent!", HttpStatus.OK.value()));
	}

}
