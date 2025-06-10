package com.example.demo.adminServices;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entities.Category;
import com.example.demo.entities.ProductImage;
import com.example.demo.entities.Products;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ProductImageRepository;
import com.example.demo.repositories.ProductRepository;

@Service
public class AdminProductService {

	ProductRepository productRepository;
	ProductImageRepository productImageRepository;
	CategoryRepository categoryRepository;
	
	public AdminProductService(ProductRepository productRepository,ProductImageRepository productImageRepository,CategoryRepository categoryRepository) {
		
		this.productRepository = productRepository;
		this.productImageRepository = productImageRepository;
		this.categoryRepository = categoryRepository;
	}
	
	
	public Products addProductWithImage(String name, String description,String imageUrl, Double price, Integer stock, Integer categoryId) {
		
		Optional<Category> category =  categoryRepository.findById(categoryId);
		
		if(category.isEmpty()) {
			throw new IllegalArgumentException("Invalid categoryId");
		}
		Products product = new Products(name, description, BigDecimal.valueOf(price), stock, category.get(), LocalDateTime.now(), LocalDateTime.now());
		Products savedProduct = productRepository.save(product);
		
		if(imageUrl != null && !imageUrl.isEmpty()) {
			ProductImage productImage = new ProductImage(savedProduct, imageUrl);
			productImageRepository.save(productImage);
		}
		else {
			throw new IllegalArgumentException("Product Image Cannot be Empty");
		}
		return savedProduct;
	}
	
	public void deleteProduct(int productId) {
		productImageRepository.deleteByProductId(productId);
		productRepository.deleteById(productId);
	}
}
