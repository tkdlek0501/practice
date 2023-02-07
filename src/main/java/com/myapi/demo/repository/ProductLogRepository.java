package com.myapi.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapi.demo.domain.ProductLog;

public interface ProductLogRepository extends JpaRepository<ProductLog, Long>{
	
}
