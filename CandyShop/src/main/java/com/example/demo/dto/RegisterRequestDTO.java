package com.example.demo.dto;

import java.time.LocalDate;

import com.example.demo.model.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
	private String userName;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String email;
	private Gender gender;
	private String password;
	private LocalDate birthDay;
}
