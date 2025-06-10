package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="productimages")
public class ProductImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer imageId;
	
	@ManyToOne
	@JoinColumn(name= "product_id")
	private Products product;
	
	@Column
	private String imageUrl;
	
	public ProductImage() {
		// TODO Auto-generated constructor stub
	}

	public ProductImage(Integer imageId, Products product, String imageUrl) {
		super();
		this.imageId = imageId;
		this.product = product;
		this.imageUrl = imageUrl;
	}

	public ProductImage(Products product, String imageUrl) {
		super();
		this.product=product;
		this.imageUrl = imageUrl;
	}

	public Integer getImageId() {
		return imageId;
	}

	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}

	public Products getProduct() {
		return product;
	}

	public void setProduct(Products product) {
		this.product = product;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	
}
