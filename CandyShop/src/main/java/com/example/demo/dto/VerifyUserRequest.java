package com.example.demo.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyUserRequest {
	
	@Pattern(regexp = "^[0-9]{6,6}$", message = "OTP must be a number")
	private String otp;
	
}
