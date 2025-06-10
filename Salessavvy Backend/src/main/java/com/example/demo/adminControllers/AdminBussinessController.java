package com.example.demo.adminControllers;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.adminServices.AdminBussinessService;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/admin/bussiness")
public class AdminBussinessController {

	AdminBussinessService adminBussinessService;
	
	public AdminBussinessController(AdminBussinessService adminBussinessService) {
		this.adminBussinessService = adminBussinessService;
	}
	
	@GetMapping("/monthly")
	public ResponseEntity<?> calculateMonthlyBusiness(@RequestParam int month, int year) {
		try {
			Map<String, Object> response = adminBussinessService.calculateMonthlyBussiness(month, year);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something Went Wrong..");
		}
		
	}
	
	@GetMapping("/daily")
	public ResponseEntity<?> calculateDailyBussiness(@RequestParam String date) {
		try {
			LocalDate localDate = LocalDate.parse(date);
			Map<String, Object> response = adminBussinessService.calculateDailyBussiness(localDate);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something Went Wrong..");
		}
	}
	
	@GetMapping("/yearly")
	public ResponseEntity<?> calculateYearlyBussiness(@RequestParam int year) {
		try {
			Map<String, Object> response = adminBussinessService.calculateYearlyBussiness(year);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something Went Wrong..");
		}
	}
	
	@GetMapping("/overAll")
	public ResponseEntity<?> calculateOverAllBussiness() {
		try {
			Map<String, Object> response = adminBussinessService.calculateOverAllBussiness();
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something Went Wrong..");
		}
	}
}
