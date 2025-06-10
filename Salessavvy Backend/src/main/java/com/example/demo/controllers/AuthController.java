package com.example.demo.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginDTO;
import com.example.demo.entities.User;
import com.example.demo.services.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/api/auth")
public class AuthController {
	
	AuthService service;
	public AuthController(AuthService service) {
	this.service = service;
	}

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginDTO login, HttpServletResponse response) {
		try {
			User user = service.authUser(login.getUsername(), login.getPassword()); 
			String token = service.generateToken(user);
			Cookie cookie = new Cookie("authToken", token);
			cookie.setHttpOnly(true);
			cookie.setSecure(false);
			cookie.setPath("/");
			cookie.setMaxAge(3600);
			cookie.setDomain("localhost");
			response.addCookie(cookie);
			response.addHeader("Set-Cookie",
                    String.format("authToken=%s; HttpOnly; Path=/; Max-Age=3600; SameSite=None", token));
			
			HashMap<String, Object> responseBody = new HashMap<>();
			responseBody.put("message", "Login successful");
			responseBody.put("role", user.getRole().name());
			responseBody.put("username", user.getUsername());
			
			
			return ResponseEntity.ok(responseBody);
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("Error", e.getMessage()));
		}
	}
	
	@PostMapping("/logout")
	public ResponseEntity<Map<String, String>> logout(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			User user =  (User)request.getAttribute("authenticatedUser");
			
			service.logout(user);
			
			Cookie cookie = new Cookie("authToken", null);
			cookie.setHttpOnly(true);
			cookie.setMaxAge(3600);
			cookie.setPath("/");
			
			response.addCookie(cookie);
			
			Map<String, String> responseBody = new HashMap<>();
			responseBody.put("message", "Logout Successful..!");
			return ResponseEntity.ok(responseBody);
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Logout Failed"));
		}
	}
}
