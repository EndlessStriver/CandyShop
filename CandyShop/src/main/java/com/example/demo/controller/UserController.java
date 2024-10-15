package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.AddressRequestDTO;
import com.example.demo.dto.ApiResponseDTO;
import com.example.demo.dto.ChangeEmailRequestDTO;
import com.example.demo.dto.ChangePasswordRequestDTO;
import com.example.demo.dto.UserProfileRequestDTO;
import com.example.demo.dto.VerifyUserRequest;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Address;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PatchMapping("/{userId}")
	public ResponseEntity<?> updateUser(@PathVariable String userId,
			@RequestBody UserProfileRequestDTO userProfileRequestDTO) throws ResourceNotFoundException, Exception {
		User user = userService.updateUser(userId, userProfileRequestDTO);
		ApiResponseDTO<User> response = new ApiResponseDTO<>("User updated successfully", HttpStatus.OK.value(), user);
		return ResponseEntity.ok(response);
	}

	@PatchMapping("/{userId}/password")
	public ResponseEntity<?> changePassword(@PathVariable String userId,
			@RequestBody ChangePasswordRequestDTO changePasswordRequestDTO)
			throws ResourceNotFoundException, Exception {
		userService.changePassword(userId, changePasswordRequestDTO);
		ApiResponseDTO<String> response = new ApiResponseDTO<>("Password changed successfully", HttpStatus.OK.value(),
				null);
		return ResponseEntity.ok(response);
	}

	@PatchMapping("/{userId}/avatar")
	public ResponseEntity<?> uploadAvatar(@PathVariable String userId,
			@RequestPart(value = "file") MultipartFile multipartFile) throws Exception {
		User user = userService.uploadAvatar(userId, multipartFile);
		ApiResponseDTO<User> response = new ApiResponseDTO<>("Avatar uploaded successfully", HttpStatus.OK.value(),
				user);
		return ResponseEntity.ok(response);
	}

	@PatchMapping("/{userId}/email")
	public ResponseEntity<?> changeEmail(@PathVariable String userId,
			@RequestBody ChangeEmailRequestDTO changeEmailRequestDTO) throws Exception {
		User user = userService.changeEmail(userId, changeEmailRequestDTO);
		ApiResponseDTO<User> response = new ApiResponseDTO<>("Email changed successfully", HttpStatus.OK.value(), user);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/{userId}/addresses")
	public ResponseEntity<?> createAddress(@PathVariable String userId, @RequestBody AddressRequestDTO address)
			throws Exception {
		Address newAddress = userService.createAddress(userId, address);
		ApiResponseDTO<Address> response = new ApiResponseDTO<>("Address created successfully",
				HttpStatus.CREATED.value(), newAddress);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PatchMapping("/{userId}/addresses/{addressId}")
	public ResponseEntity<?> updateAddress(@PathVariable String userId, @PathVariable String addressId,
			@RequestBody AddressRequestDTO address) throws Exception {
		Address updatedAddress = userService.updateAddress(userId, addressId, address);
		ApiResponseDTO<Address> response = new ApiResponseDTO<>("Address updated successfully", HttpStatus.OK.value(),
				updatedAddress);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{userId}/addresses/{addressId}")
	public ResponseEntity<?> deleteAddress(@PathVariable String userId, @PathVariable String addressId)
			throws Exception {
		userService.deleteAddress(userId, addressId);
		ApiResponseDTO<String> response = new ApiResponseDTO<>("Address deleted successfully", HttpStatus.OK.value(),
				null);
		return ResponseEntity.ok(response);
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
	
	@PostMapping("/{userId}/verify")
	public ResponseEntity<?> verifyUser(@PathVariable String userId, @RequestBody VerifyUserRequest verifyUserRequest) throws Exception {
		User user = userService.verifyUser(userId, verifyUserRequest);
		ApiResponseDTO<User> response = new ApiResponseDTO<>("User verified successfully", HttpStatus.OK.value(), user);
		return ResponseEntity.ok(response);
	}

}
