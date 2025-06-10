package com.example.demo.adminServices;

import org.springframework.stereotype.Service;

import com.example.demo.entities.User;
import com.example.demo.repositories.JWTTokenRepository;
import com.example.demo.repositories.UserRepository;

@Service
public class AdminLogoutService {

	UserRepository userRepository;
	JWTTokenRepository jwtTokenRepository;
	
	public AdminLogoutService(UserRepository userRepository, JWTTokenRepository jwtTokenRepository) {
		this.userRepository = userRepository;
		this.jwtTokenRepository = jwtTokenRepository;
	}
	
	public void adminLogout(User user) {
		jwtTokenRepository.deleteByUserId(user.getUserId());
	}
}
