package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeEmailRequestDTO {
	
	@NotBlank(message = "New email is required")
	@Email(message = "New email is invalid")
	private String newEmail;
	
	@NotBlank(message = "Password is required")
	private String password;
	
	@Pattern(regexp = "^[0-9]{6,6}$", message = "OTP must be numeric and have 6 characters")
	private String otp;
}
