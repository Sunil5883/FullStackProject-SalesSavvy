package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entities.Category;
import com.example.demo.entities.ProductImage;
import com.example.demo.entities.Products;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ProductImageRepository;
import com.example.demo.repositories.ProductRepository;

@Service
public class ProductService {

	ProductRepository productRepository;
	ProductImageRepository productImageRepository;
	CategoryRepository categoryRepository;
	
	public ProductService(ProductRepository productRepository, ProductImageRepository productImageRepository,
			CategoryRepository categoryRepository) {
		this.productRepository = productRepository;
		this.productImageRepository = productImageRepository;
		this.categoryRepository = categoryRepository;
	}
	
	public List<Products> getProductsByCategoryName(String categoryName) {
		if(categoryName != null && !categoryName.isEmpty()) {
			Optional<Category> categoryOpt = categoryRepository.findBycategoryName(categoryName);
			if(categoryOpt.isPresent()) {
				Category category = categoryOpt.get();
				return productRepository.findByCategory_categoryId(category.getCategoryId());
			}
			else {
				throw new RuntimeException("Category not found");
			}
		}
		else {
			return productRepository.findAll();
		}
	}
	
	public List<String> getProductImageByProductId(Integer productId) {
		
		List<ProductImage> productImage = productImageRepository.findByProduct_ProductId(productId);
		
			List<String> imageUrls = new ArrayList<>();
		for (ProductImage image : productImage) {
			imageUrls.add(image.getImageUrl());
		}
		return imageUrls;
	}
}
