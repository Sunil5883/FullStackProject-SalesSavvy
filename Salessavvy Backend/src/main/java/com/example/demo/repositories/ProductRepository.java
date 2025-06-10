package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Products;

@Repository
public interface ProductRepository extends JpaRepository<Products, Integer> {

	List<Products> findByCategory_categoryId(Integer categoryId);

	@Query("SELECT p.category.categoryName FROM Products p where p.productId= :productId")
	public String findCategoryNameByProductId(int productId);
}
