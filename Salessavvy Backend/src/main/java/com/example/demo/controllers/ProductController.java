package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Products;
import com.example.demo.entities.User;
import com.example.demo.services.ProductService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/api/products")
public class ProductController {

	ProductService service;
	
	public ProductController(ProductService service) {
	this.service = service;
	}
	
	@GetMapping
	public ResponseEntity<Map<String, Object>> getProducts(@RequestParam String category, HttpServletRequest request) {
		
	User authenticatedUser = (User) request.getAttribute("authenticatedUser");
	try {	
	if(authenticatedUser == null) {
			return ResponseEntity.status(401).body(Map.of("Error", "Unauthorized Access"));
		}
		List<Products> products = service.getProductsByCategoryName(category);
		Map<String, Object> response = new HashMap<>();
		
		Map<String, Object> userdetails = new HashMap<>();
			userdetails.put("username", authenticatedUser.getUsername());
			userdetails.put("role", authenticatedUser.getRole());
		response.put("user", userdetails);
		
		List<Map<String, Object>> productList = new ArrayList<>();
		
		for (Products product : products) {
		Map<String, Object> productDetails = new HashMap<>();
		productDetails.put("productId", product.getProductId());
		productDetails.put("name", product.getName());
		productDetails.put("description", product.getDescription());
		productDetails.put("price", product.getPrice());
		productDetails.put("stock", product.getStock());
		
		List<String> images= service.getProductImageByProductId(product.getProductId());
		productDetails.put("images", images);
		productList.add(productDetails);
		}
		
		response.put("products", productList);
		return ResponseEntity.ok().body(response);
	}
	catch (Exception e) {
		return ResponseEntity.badRequest().body(Map.of("Error", e.getMessage()));
	}
	}
}
