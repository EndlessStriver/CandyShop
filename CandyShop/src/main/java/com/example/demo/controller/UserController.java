package com.example.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.AddressRequestDTO;
import com.example.demo.dto.AddressRequestUpdateDTO;
import com.example.demo.dto.ApiResponseDTO;
import com.example.demo.dto.ApiResponseErrorDTO;
import com.example.demo.dto.ApiResponseNoDataDTO;
import com.example.demo.dto.ChangeEmailRequestDTO;
import com.example.demo.dto.ChangePasswordRequestDTO;
import com.example.demo.dto.OrderPageResponseDTO;
import com.example.demo.dto.PagedResponseDTO;
import com.example.demo.dto.UserProfileRequestDTO;
import com.example.demo.dto.VerifyUserRequest;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Address;
import com.example.demo.model.User;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private UserService userService;
	private OrderService orderService;

	public UserController(UserService userService, OrderService orderService) {
		this.userService = userService;
		this.orderService = orderService;
	}

	@GetMapping("/{userId}/addresses/{addressId}")
	public ResponseEntity<?> getAddress(@PathVariable String userId, @PathVariable String addressId) throws Exception {
		Address address = userService.getAddress(userId, addressId);
		ApiResponseDTO<Address> response = new ApiResponseDTO<>("Address retrieved successfully", HttpStatus.OK.value(),
				address);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{userId}/addresses")
	public ResponseEntity<?> getAddresses(@PathVariable String userId) throws Exception {
		List<Address> addresses = userService.getAddresses(userId);
		ApiResponseDTO<List<Address>> response = new ApiResponseDTO<>("Addresses retrieved successfully",
				HttpStatus.OK.value(), addresses);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{userId}/orders")
	public ResponseEntity<?> getOrders(@PathVariable String userId, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "0") int limit, @RequestParam(defaultValue = "createdAt") String sortField,
			@RequestParam(defaultValue = "asc") String sortOrder) throws Exception {
		PagedResponseDTO<OrderPageResponseDTO> pagedResponseDTO = orderService.getOrdersByUserId(userId, page, limit,
				sortField, sortOrder);
		ApiResponseDTO<PagedResponseDTO<OrderPageResponseDTO>> response = new ApiResponseDTO<>(
				"Orders retrieved successfully", HttpStatus.OK.value(), pagedResponseDTO);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<?> getUser(@PathVariable String userId) throws ResourceNotFoundException {
		User user = userService.getUserById(userId);
		ApiResponseDTO<User> response = new ApiResponseDTO<>("User retrieved successfully", HttpStatus.OK.value(),
				user);
		return ResponseEntity.ok(response);
	}

	@PatchMapping("/{userId}")
	public ResponseEntity<?> updateUser(@PathVariable String userId,
			@Valid @RequestBody UserProfileRequestDTO userProfileRequestDTO, BindingResult bindingResult)
			throws ResourceNotFoundException, Exception {
		logger.info("User profile request: {}", userProfileRequestDTO);
		if (bindingResult.hasErrors()) {
			Map<String, String> errors = bindingResult.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
			ApiResponseErrorDTO apiResponseErrorDTO = new ApiResponseErrorDTO("Validation failed",
					HttpStatus.BAD_REQUEST.value(), errors);
			logger.error("Validation failed: {}", errors);
			return new ResponseEntity<ApiResponseErrorDTO>(apiResponseErrorDTO, HttpStatus.BAD_REQUEST);
		}
		User user = userService.updateUser(userId, userProfileRequestDTO);
		ApiResponseDTO<User> response = new ApiResponseDTO<>("User updated successfully", HttpStatus.OK.value(), user);
		logger.info("User updated successfully: {}", user);
		return ResponseEntity.ok(response);
	}

	@PatchMapping("/{userId}/password")
	public ResponseEntity<?> changePassword(@PathVariable String userId,
			@Valid @RequestBody ChangePasswordRequestDTO changePasswordRequestDTO, BindingResult bindingResult)
			throws ResourceNotFoundException, Exception {
		logger.info("Change password request for user: {}", userId);
		if (bindingResult.hasErrors()) {
			Map<String, String> errors = bindingResult.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
			ApiResponseErrorDTO apiResponseErrorDTO = new ApiResponseErrorDTO("Validation failed",
					HttpStatus.BAD_REQUEST.value(), errors);
			logger.error("Validation failed: {}", errors);
			return new ResponseEntity<ApiResponseErrorDTO>(apiResponseErrorDTO, HttpStatus.BAD_REQUEST);
		}
		userService.changePassword(userId, changePasswordRequestDTO);
		ApiResponseNoDataDTO response = new ApiResponseNoDataDTO("Password changed successfully",
				HttpStatus.OK.value());
		logger.info("Password changed successfully for user: {}", userId);
		return ResponseEntity.ok(response);
	}

	@PatchMapping("/{userId}/avatar")
	public ResponseEntity<?> uploadAvatar(@PathVariable String userId,
			@RequestPart(value = "file") MultipartFile multipartFile) throws Exception {
		logger.info("Upload avatar request for user: {}", userId);
		User user = userService.uploadAvatar(userId, multipartFile);
		ApiResponseDTO<User> response = new ApiResponseDTO<>("Avatar uploaded successfully", HttpStatus.OK.value(),
				user);
		logger.info("Avatar uploaded successfully for user: {}", userId);
		return ResponseEntity.ok(response);
	}

	@PatchMapping("/{userId}/email")
	public ResponseEntity<?> changeEmail(@PathVariable String userId,
			@Valid @RequestBody ChangeEmailRequestDTO changeEmailRequestDTO, BindingResult bindingResult)
			throws Exception {
		logger.info("Change email request for user id: " + userId + " email: " + changeEmailRequestDTO.getNewEmail());
		if (bindingResult.hasErrors()) {
			Map<String, String> errors = bindingResult.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
			ApiResponseErrorDTO apiResponseErrorDTO = new ApiResponseErrorDTO("Validation failed",
					HttpStatus.BAD_REQUEST.value(), errors);
			logger.error("Validation failed: {}", errors);
			return new ResponseEntity<ApiResponseErrorDTO>(apiResponseErrorDTO, HttpStatus.BAD_REQUEST);
		}
		User user = userService.changeEmail(userId, changeEmailRequestDTO);
		ApiResponseDTO<User> response = new ApiResponseDTO<>("Email changed successfully", HttpStatus.OK.value(), user);
		logger.info(
				"Email changed successfully for user id: " + userId + " email: " + changeEmailRequestDTO.getNewEmail());
		return ResponseEntity.ok(response);
	}

	@PostMapping("/{userId}/addresses")
	public ResponseEntity<?> createAddress(@PathVariable String userId, @Valid @RequestBody AddressRequestDTO address,
			BindingResult bindingResult) throws Exception {
		logger.info("Create address request for user id: " + userId + " address: " + address);
		if (bindingResult.hasErrors()) {
			Map<String, String> errors = bindingResult.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
			ApiResponseErrorDTO apiResponseErrorDTO = new ApiResponseErrorDTO("Validation failed",
					HttpStatus.BAD_REQUEST.value(), errors);
			logger.error("Validation failed: {}", errors);
			return new ResponseEntity<ApiResponseErrorDTO>(apiResponseErrorDTO, HttpStatus.BAD_REQUEST);
		}
		Address newAddress = userService.createAddress(userId, address);
		ApiResponseDTO<Address> response = new ApiResponseDTO<>("Address created successfully",
				HttpStatus.CREATED.value(), newAddress);
		logger.info("Address created successfully for user id: " + userId + " address: " + address);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PatchMapping("/{userId}/addresses/{addressId}")
	public ResponseEntity<?> updateAddress(@PathVariable String userId, @PathVariable String addressId,
			@Valid @RequestBody AddressRequestUpdateDTO addressRequestUpdateDTO, BindingResult bindingResult)
			throws Exception {
		logger.info("Update address request for user id: " + userId + " address id: " + addressId + " address: "
				+ addressRequestUpdateDTO);
		if (bindingResult.hasErrors()) {
			Map<String, String> errors = bindingResult.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
			ApiResponseErrorDTO apiResponseErrorDTO = new ApiResponseErrorDTO("Validation failed",
					HttpStatus.BAD_REQUEST.value(), errors);
			logger.error("Validation failed: {}", errors);
			return new ResponseEntity<ApiResponseErrorDTO>(apiResponseErrorDTO, HttpStatus.BAD_REQUEST);
		}
		Address updatedAddress = userService.updateAddress(userId, addressId, addressRequestUpdateDTO);
		ApiResponseDTO<Address> response = new ApiResponseDTO<>("Address updated successfully", HttpStatus.OK.value(),
				updatedAddress);
		logger.info("Address updated successfully for user id: " + userId + " address id: " + addressId + " address: "
				+ addressRequestUpdateDTO);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{userId}/addresses/{addressId}")
	public ResponseEntity<?> deleteAddress(@PathVariable String userId, @PathVariable String addressId)
			throws Exception {
		logger.info("Delete address request for user id: " + userId + " address id: " + addressId);
		userService.deleteAddress(userId, addressId);
		ApiResponseNoDataDTO response = new ApiResponseNoDataDTO("Address deleted successfully", HttpStatus.OK.value());
		logger.info("Address deleted successfully for user id: " + userId + " address id: " + addressId);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/{userId}/verify")
	public ResponseEntity<?> verifyUser(@PathVariable String userId,
			@Valid @RequestBody VerifyUserRequest verifyUserRequest, BindingResult bindingResult) throws Exception {
		logger.info("Verify user request for user id: " + userId + " code: " + verifyUserRequest.getOtp());
		if (bindingResult.hasErrors()) {
			Map<String, String> errors = bindingResult.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
			ApiResponseErrorDTO apiResponseErrorDTO = new ApiResponseErrorDTO("Validation failed",
					HttpStatus.BAD_REQUEST.value(), errors);
			logger.error("Validation failed: {}", errors);
			return new ResponseEntity<ApiResponseErrorDTO>(apiResponseErrorDTO, HttpStatus.BAD_REQUEST);
		}
		User user = userService.verifyUser(userId, verifyUserRequest);
		ApiResponseDTO<User> response = new ApiResponseDTO<>("User verified successfully", HttpStatus.OK.value(), user);
		logger.info("User verified successfully for user id: " + userId + " code: " + verifyUserRequest.getOtp());
		return ResponseEntity.ok(response);
	}

}
