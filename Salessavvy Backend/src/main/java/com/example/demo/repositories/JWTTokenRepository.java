package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.JWTTokens;

import jakarta.transaction.Transactional;

@Repository
public interface JWTTokenRepository extends JpaRepository<JWTTokens, Integer>{

	@Query("SELECT t FROM JWTTokens t WHERE t.user.userId = :userId")
	public JWTTokens findByUserId(int userId);
	
	public Optional<JWTTokens> findByToken(String token);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM JWTTokens t WHERE t.user.userId = :userId")
	public void deleteByUserId(int userId);
}
