package com.example.demo.adminControllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.adminServices.AdminProductService;
import com.example.demo.entities.Products;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/admin/products")
public class AdminProductController {

	
	AdminProductService adminProductService;
	
	public AdminProductController(AdminProductService adminProductService) {
	this.adminProductService = adminProductService;
	}
	
	
	@PostMapping("/add") 
	public ResponseEntity<?> addproduct(@RequestBody Map<String, Object> requestBody) {
		try {
		String name = (String) requestBody.get("name");
		String description = (String) requestBody.get("description");
		String imageUrl = (String) requestBody.get("imageUrl");
		Double price = Double.valueOf(String.valueOf(requestBody.get("price")));
		Integer stock = (Integer) requestBody.get("stock");
		Integer categoryId = (Integer) requestBody.get("categoryId");
	
		Products addedProduct = adminProductService.addProductWithImage(name, description, imageUrl, price, stock, categoryId);
		Map<String, Object> response = new HashMap<>();
		response.put("product", addedProduct);
		response.put("imageUrl", imageUrl);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something Went Wrong..");
		}
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteProduct(@RequestBody Map<String, Object> requestBody) {
		try {
			Integer productId = (Integer) requestBody.get("productId");
			adminProductService.deleteProduct(productId);
			return ResponseEntity.status(HttpStatus.OK).body("Product Deleted Successfully..");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something Wnet Wrong..");
		}
	}
}
