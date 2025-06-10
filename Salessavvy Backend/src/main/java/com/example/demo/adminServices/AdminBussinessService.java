package com.example.demo.adminServices;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.entities.Order;
import com.example.demo.entities.OrderItem;
import com.example.demo.entities.OrderStatus;
import com.example.demo.repositories.OrderItemRepository;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.ProductRepository;

@Service
public class AdminBussinessService {

	OrderRepository orderRepository;
	OrderItemRepository orderItemRepository;
	ProductRepository productRepository;

	public AdminBussinessService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ProductRepository productRepository) {
		this.orderRepository = orderRepository;
		this.orderItemRepository = orderItemRepository;
		this.productRepository = productRepository;
	}
	
	public Map<String, Object> calculateMonthlyBussiness(int month, int year) {
		
		if(year < 2025 || year > 2025) {
			throw new IllegalArgumentException("Invalid Year:" + year);
		}
		List<Order> successfullOrders = orderRepository.findSuccessfulOrdersByMothAndYear(month, year);
		double totalBussiness = 0.0;
		
		Map<String, Integer> categorySales = new HashMap<>();
		for (Order order : successfullOrders) {
			totalBussiness += order.getTotalAmount().doubleValue();
			List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getOrderId());
			for(OrderItem item : orderItems) {
				String categoryName = productRepository.findCategoryNameByProductId(item.getProductId());
				categorySales.put(categoryName, categorySales.getOrDefault(categoryName, 0)+ item.getQuantity());
			}
		}
		Map<String, Object> bussinessReport = new HashMap<>();
		bussinessReport.put("totalBusiness", totalBussiness);
		bussinessReport.put("categorySales", categorySales);
		return bussinessReport;
	}
	
	public Map<String, Object> calculateDailyBussiness(LocalDate date) {
		if(date == null) {
			throw new IllegalArgumentException("Date Cannot be Taken As Null");
		}
		List<Order> successfullOrders = orderRepository.findSuccessfulOrdersByDate(date);
		double totalBussiness = 0.0;
		
		Map<String, Integer> categorySales = new HashMap<>();
		for (Order order : successfullOrders) {
			totalBussiness += order.getTotalAmount().doubleValue();
			List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getOrderId());
			for(OrderItem item : orderItems) {
				String categoryName = productRepository.findCategoryNameByProductId(item.getProductId());
				categorySales.put(categoryName, categorySales.getOrDefault(categoryName, 0)+ item.getQuantity());
			}
		}
		Map<String, Object> bussinessReport = new HashMap<>();
		bussinessReport.put("totalBusiness", totalBussiness);
		bussinessReport.put("categorySales", categorySales);
		return bussinessReport;
			
		}
	
	public Map<String, Object> calculateYearlyBussiness(int year) {
		
		if(year < 2025 || year > 2025) {
			throw new IllegalArgumentException("Invalid Year:" + year);
		}
		List<Order> successfullOrders = orderRepository.findSuccessfulOrdersByYear(year);
		double totalBussiness = 0.0;
		
		Map<String, Integer> categorySales = new HashMap<>();
		for (Order order : successfullOrders) {
			totalBussiness += order.getTotalAmount().doubleValue();
			List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getOrderId());
			for(OrderItem item : orderItems) {
				String categoryName = productRepository.findCategoryNameByProductId(item.getProductId());
				categorySales.put(categoryName, categorySales.getOrDefault(categoryName, 0)+ item.getQuantity());
			}
		}
		Map<String, Object> bussinessReport = new HashMap<>();
		bussinessReport.put("totalBusiness", totalBussiness);
		bussinessReport.put("categorySales", categorySales);
		return bussinessReport;
	}
	
	public Map<String, Object> calculateOverAllBussiness() {
		
		BigDecimal totalOverAllBussiness = orderRepository.calculateOverAllBussiness();
		List<Order>  successfullOrders= orderRepository.findAllByStatus(OrderStatus.SUCCESS.name());
		Map<String, Integer> categorySales = new HashMap<>();
		for (Order order : successfullOrders) {
			List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getOrderId());
			for(OrderItem item : orderItems) {
				String categoryName = productRepository.findCategoryNameByProductId(item.getProductId());
				categorySales.put(categoryName, categorySales.getOrDefault(categoryName, 0)+ item.getQuantity());
			}
		}
		Map<String, Object> bussinessReport = new HashMap<>();
		bussinessReport.put("totalBusiness", totalOverAllBussiness.doubleValue());
		bussinessReport.put("categorySales", categorySales);
		return bussinessReport;
	}
	
}
