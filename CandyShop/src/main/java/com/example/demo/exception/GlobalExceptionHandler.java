package com.example.demo.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex) {
        Map<String, Object> errors = new LinkedHashMap<String, Object>();
        errors.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errors.put("message", ex.getMessage());
        return new ResponseEntity<Map<String, Object>>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException ex){
		Map<String, Object> errors = new LinkedHashMap<String, Object>();
        errors.put("status", HttpStatus.NOT_FOUND.value());
        errors.put("message", ex.getMessage());
        return new ResponseEntity<Map<String, Object>>(errors, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<Map<String, Object>> handleLoginFailedException(UnauthorizedException ex){
		Map<String, Object> errors = new LinkedHashMap<String, Object>();
        errors.put("status", HttpStatus.UNAUTHORIZED.value());
        errors.put("message", ex.getMessage());
        return new ResponseEntity<Map<String, Object>>(errors, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(ResourceConflictException.class)
	public ResponseEntity<Map<String, Object>> resoureConflicException(ResourceConflictException ex){
		Map<String, Object> errors = new LinkedHashMap<String, Object>();
		errors.put(ex.getField(), ex.getMessage());
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("status", HttpStatus.CONFLICT.value());
		response.put("message", "Conflict with existing data");
		response.put("errors", errors);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<Map<String, Object>> badRequestException(BadRequestException ex){
		Map<String, Object> errors = new LinkedHashMap<String, Object>();
		errors.put(ex.getField(), ex.getMessage());
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("status", HttpStatus.BAD_REQUEST.value());
		response.put("message", "Bad Request");
		response.put("errors", errors);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
	}
	
}
