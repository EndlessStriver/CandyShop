package com.example.demo.service.imp;

import java.time.LocalDate;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ChangePasswordRequestDTO;
import com.example.demo.dto.UserProfileRequestDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.model.User;
import com.example.demo.model.enums.Gender;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Service
public class UserServiceImp implements UserService {

	private UserRepository userRepository;
	private BCryptPasswordEncoder bycryptPasswordEncoder;

	public UserServiceImp(UserRepository userRepository, BCryptPasswordEncoder bycryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.bycryptPasswordEncoder = bycryptPasswordEncoder;
	}

	@Override
	public User updateUser(String userId, UserProfileRequestDTO profileRequestDTO)
			throws Exception, ResourceNotFoundException {

		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

		String firstName = profileRequestDTO.getFirstName();
		String lastName = profileRequestDTO.getLastName();
		String phoneNumber = profileRequestDTO.getPhoneNumber();
		Gender gender = profileRequestDTO.getGender();
		LocalDate birthDay = profileRequestDTO.getBirthDay();

		if (firstName != null)
			user.setFirstName(firstName);
		if (lastName != null)
			user.setLastName(lastName);
		if (phoneNumber != null)
			user.setPhoneNumber(phoneNumber);
		if (gender != null)
			user.setGender(profileRequestDTO.getGender());
		if (birthDay != null)
			user.setBirthDay(profileRequestDTO.getBirthDay());

		return userRepository.save(user);
	}

	@Override
	public void changePassword(String userId, ChangePasswordRequestDTO changePasswordRequestDTO)
			throws Exception, ResourceNotFoundException {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		String oldPassword = changePasswordRequestDTO.getOldPassword();
		String newPassword = changePasswordRequestDTO.getNewPassword();

		if (!bycryptPasswordEncoder.matches(oldPassword, user.getPassword()))
			throw new UnauthorizedException("Old password is incorrect");
		user.setPassword(bycryptPasswordEncoder.encode(newPassword));
		userRepository.save(user);
	}

}
