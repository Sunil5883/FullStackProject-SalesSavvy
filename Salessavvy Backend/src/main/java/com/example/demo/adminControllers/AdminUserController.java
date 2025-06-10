package com.example.demo.adminControllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.adminServices.AdminUserService;
import com.example.demo.entities.User;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/admin/user")
public class AdminUserController {

	AdminUserService adminUserService;
	
	public AdminUserController(AdminUserService adminUserService) {
	this.adminUserService = adminUserService;
	}
	
	@PostMapping("/getById")
	public ResponseEntity<?> getUserById(@RequestBody Map<String, Integer> userRequest) {
		try {
		int userId = userRequest.get("userId");
		User user = adminUserService.getUserById(userId);
		return ResponseEntity.status(HttpStatus.OK).body(user);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something Went Wrong..");
		}
	}
	
	
	@PutMapping("/modify")
	public ResponseEntity<?> modifyUser(@RequestBody Map<String , Object> userRequest) {
		
		try {
			Integer userId = (Integer) userRequest.get("userId");
			String username = (String) userRequest.get("username");
			String email = (String) userRequest.get("email");
			String role = (String) userRequest.get("role");
			
			User modifiedUser = adminUserService.modifyUser(userId, username, email, role);
			
			Map<String, Object> responseBody = new HashMap<>();
			responseBody.put("userId", modifiedUser.getUserId());
			responseBody.put("username", modifiedUser.getUsername());
			responseBody.put("email", modifiedUser.getEmail());
			responseBody.put("role", modifiedUser.getRole().name());
			responseBody.put("createdAt", modifiedUser.getCreatedAt());
			responseBody.put("updatedAt", modifiedUser.getUpdatedAt());
			
			return ResponseEntity.status(HttpStatus.OK).body(responseBody);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something Went Wrong");
		}
	}
}
