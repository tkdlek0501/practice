package com.myapi.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapi.demo.domain.Product;
import com.myapi.demo.repository.custom.ProductRepositoryCustom;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom{
	
	//Optional<Product> findByIdAndExpiredAtIsNull(Long id);
	
}
