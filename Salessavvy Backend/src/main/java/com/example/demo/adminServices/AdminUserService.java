package com.example.demo.adminServices;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.repositories.JWTTokenRepository;
import com.example.demo.repositories.UserRepository;

@Service
public class AdminUserService {

	UserRepository userRepository;
	JWTTokenRepository jwtTokenRepository;
	
	public AdminUserService(UserRepository userRepository, JWTTokenRepository jwtTokenRepository) {
		this.userRepository = userRepository;
		this.jwtTokenRepository = jwtTokenRepository;
	}
	
	
	public User getUserById(int userId) {
		return userRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("User Not Found With UserId: "+ userId));
	}
	
	public User modifyUser(Integer userId, String username, String email, String role) throws IllegalAccessException {
		
		Optional<User> optionalUser = userRepository.findById(userId);
		
		if(optionalUser.isEmpty()) {
			throw new IllegalAccessException("User Not Found With userId" + userId);
		}
		User existingUser = optionalUser.get();
		if (username != null && !username.isEmpty()) {
			existingUser.setUsername(username);	
		}
		if(email != null && !email.isEmpty()) {
			existingUser.setEmail(email);
		}
		if(role != null && !role.isEmpty()) {
			existingUser.setRole(Role.valueOf(role));
		}
		
		jwtTokenRepository.deleteByUserId(existingUser.getUserId());
		return userRepository.save(existingUser);
	}
}
