package com.example.demo.service.imp;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.ChangePasswordRequestDTO;
import com.example.demo.dto.UserProfileRequestDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.model.User;
import com.example.demo.model.enums.Gender;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.S3Service;
import com.example.demo.service.UserService;

@Service
public class UserServiceImp implements UserService {

	private UserRepository userRepository;
	private BCryptPasswordEncoder bycryptPasswordEncoder;
	private S3Service s3Service;
	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;

	public UserServiceImp(UserRepository userRepository, BCryptPasswordEncoder bycryptPasswordEncoder,
			S3Service s3Service) {
		this.userRepository = userRepository;
		this.bycryptPasswordEncoder = bycryptPasswordEncoder;
		this.s3Service = s3Service;
	}

	@Override
	@Transactional
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
	@Transactional
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

	@Override
	@Transactional
	public User uploadAvatar(String userId, MultipartFile multipartFile) throws Exception {
		String avatarName = null;
		try {
			User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
			if (user.getAvatar() != null && user.getAvatarUrl() != null) s3Service.deleteFile(user.getAvatar());
	        avatarName = s3Service.uploadFile(multipartFile);
	        String avatarUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, "ap-southeast-1", avatarName);
	        user.setAvatar(avatarName);
	        user.setAvatarUrl(avatarUrl);
	        return userRepository.save(user);
		} catch (Exception e) {
			if (avatarName != null) s3Service.deleteFile(avatarName);
			throw e;
		}
    }

}
