package com.example.demo.service.imp;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.exception.ResourceConflictException;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AuthService;

@Service
public class AuthServiceImp implements AuthService {

	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public AuthServiceImp(AuthenticationManager authenticationManager, UserRepository userRepository,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public void login(LoginRequestDTO loginRequestDTO) throws Exception, UnauthorizedException {
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));
			authentication.isAuthenticated();
		} catch (BadCredentialsException e) {
			throw new UnauthorizedException(e.getMessage());
		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional
	@Override
	public User register(RegisterRequestDTO registerRequestDTO) throws Exception, ResourceConflictException {
		try {

			if (userRepository.existsByUserName(registerRequestDTO.getUserName()) == true)
				throw new ResourceConflictException("Username already exists");

			User user = new User();
			user.setUserName(registerRequestDTO.getUserName());
			user.setFirstName(registerRequestDTO.getFirstName());
			user.setLastName(registerRequestDTO.getLastName());
			user.setPhoneNumber(registerRequestDTO.getPhoneNumber());
			user.setEmail(registerRequestDTO.getEmail());
			user.setGender(registerRequestDTO.getGender());
			user.setPassword(bCryptPasswordEncoder.encode(registerRequestDTO.getPassword()));
			user.setBirthDay(registerRequestDTO.getBirthDay());

			return userRepository.save(user);

		} catch (DataIntegrityViolationException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

}
