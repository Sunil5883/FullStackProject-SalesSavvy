package com.example.demo.repositories;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, String>{

	@Query("SELECT o FROM Order o WHERE MONTH(o.createdAt) = :month AND YEAR(o.createdAt) = :year AND o.status = 'SUCCESS'")
	public List<Order> findSuccessfulOrdersByMothAndYear(int month, int year);
	
	@Query("SELECT o FROM Order o WHERE DATE(o.createdAt) = :date AND o.status = 'SUCCESS'")
	public List<Order> findSuccessfulOrdersByDate(LocalDate date);
	
	@Query("SELECT o FROM Order o WHERE YEAR(o.createdAt) = :year AND o.status = 'SUCCESS'")
	public List<Order> findSuccessfulOrdersByYear(int year);
	
	@Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.status = 'SUCCESS'")
	BigDecimal calculateOverAllBussiness();
	
	@Query("SELECT o FROM Order o WHERE o.status = 'SUCCESS'")
	public List<Order> findAllByStatus(String status);
}
