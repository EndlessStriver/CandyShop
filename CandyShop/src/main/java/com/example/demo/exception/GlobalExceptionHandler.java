package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.dto.ApiResponseDTO;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleGlobalException(Exception ex) {
        String errorMessage = ex.getMessage();
        ApiResponseDTO<Void> response = new ApiResponseDTO<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
        return new ResponseEntity<ApiResponseDTO<Void>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponseDTO<Void>> handleResourceNotFoundException(ResourceNotFoundException ex){
		String errorMessage = ex.getMessage();
		ApiResponseDTO<Void> response = new ApiResponseDTO<Void>(errorMessage, HttpStatus.NOT_FOUND.value(), null);
		return new ResponseEntity<ApiResponseDTO<Void>>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ApiResponseDTO<Void>> handleLoginFailedException(UnauthorizedException ex){
		String errorMessage = ex.getMessage();
		ApiResponseDTO<Void> response = new ApiResponseDTO<Void>(errorMessage, HttpStatus.UNAUTHORIZED.value(), null);
		return new ResponseEntity<ApiResponseDTO<Void>>(response, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(ResourceConflictException.class)
	public ResponseEntity<ApiResponseDTO<Void>> handleLoginFailedException(ResourceConflictException ex){
		String errorMessage = ex.getMessage();
		ApiResponseDTO<Void> response = new ApiResponseDTO<Void>(errorMessage, HttpStatus.CONFLICT.value(), null);
		return new ResponseEntity<ApiResponseDTO<Void>>(response, HttpStatus.CONFLICT);
	}
	
}
