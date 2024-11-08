package com.example.demo.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ApiResponseDTO;
import com.example.demo.dto.ApiResponseErrorDTO;
import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.dto.SendOtpRequest;
import com.example.demo.exception.ResourceConflictException;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.model.User;
import com.example.demo.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO, BindingResult bindingResult)
			throws Exception, UnauthorizedException {
		if (bindingResult.hasErrors()) {
			Map<String, Object> errors = new LinkedHashMap<String, Object>();
			bindingResult.getFieldErrors().stream().forEach(result -> {
				errors.put(result.getField(), result.getDefaultMessage());
			});
			ApiResponseErrorDTO error = new ApiResponseErrorDTO("Login Failed!", HttpStatus.BAD_REQUEST.value(), errors);
			return ResponseEntity.badRequest().body(error);
		}
		LoginResponseDTO loginResponseDTO = authService.login(loginRequestDTO);
		return ResponseEntity.ok(new ApiResponseDTO<>("Login Success!", HttpStatus.OK.value(), loginResponseDTO));
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO, BindingResult bindingResult)
			throws Exception, ResourceConflictException {
		if (bindingResult.hasErrors()) {
			Map<String, Object> errors = new LinkedHashMap<String, Object>();
			bindingResult.getFieldErrors().stream().forEach(result -> {
				errors.put(result.getField(), result.getDefaultMessage());
			});
			ApiResponseErrorDTO error = new ApiResponseErrorDTO("Login Failed!", HttpStatus.BAD_REQUEST.value(), errors);
			return ResponseEntity.badRequest().body(error);
		}
		User user = authService.register(registerRequestDTO);
		return ResponseEntity.ok(new ApiResponseDTO<User>("Register Success!", HttpStatus.OK.value(), user));
	}

	@PostMapping("/otp")
	public ResponseEntity<?> sendOTP(@RequestBody SendOtpRequest email) throws Exception {
		authService.sendOTP(email);
		return ResponseEntity.ok(new ApiResponseDTO<>("OTP sent!", HttpStatus.OK.value(), null));
	}

}
