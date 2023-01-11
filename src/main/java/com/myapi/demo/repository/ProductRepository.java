package com.myapi.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapi.demo.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	
	//Optional<Product> findByIdAndExpiredAtIsNull(Long id);
	
}
