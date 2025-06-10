package com.example.demo.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="jwt_tokens")
public class JWTTokens {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer tokenId;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Column
	private String token;
	
	@Column
	private LocalDateTime createdAt;
	
	@Column
	private LocalDateTime expiresAt; 
	
	public JWTTokens() {
		// TODO Auto-generated constructor stub
	}

	public JWTTokens(Integer tokenId, User user, String token, LocalDateTime createdAt, LocalDateTime expiresAt) {
		super();
		this.tokenId = tokenId;
		this.user = user;
		this.token = token;
		this.createdAt = createdAt;
		this.expiresAt = expiresAt;
	}

	public JWTTokens(User user, String token, LocalDateTime createdAt, LocalDateTime expiresAt) {
		super();
		this.user = user;
		this.token = token;
		this.createdAt = createdAt;
		this.expiresAt = expiresAt;
	}

	public Integer getTokenId() {
		return tokenId;
	}

	public void setTokenId(Integer tokenId) {
		this.tokenId = tokenId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(LocalDateTime expiresAt) {
		this.expiresAt = expiresAt;
	}
	
	
}
