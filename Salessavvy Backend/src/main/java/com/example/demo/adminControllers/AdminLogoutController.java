package com.example.demo.adminControllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.adminServices.AdminLogoutService;
import com.example.demo.entities.User;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/admin")
public class AdminLogoutController {

	AdminLogoutService adminLogoutService;
	
	public AdminLogoutController(AdminLogoutService adminLogoutService) {
		this.adminLogoutService = adminLogoutService;
	}
	
	@PostMapping("/logout")
	public ResponseEntity<Map<String, String>> logout(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			User user =  (User)request.getAttribute("authenticatedUser");
			
			adminLogoutService.adminLogout(user);
			
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
