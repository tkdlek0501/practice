package com.myapi.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapi.demo.domain.PriceControlType;
import com.myapi.demo.domain.Product;
import com.myapi.demo.domain.Store;
import com.myapi.demo.repository.custom.ProductRepositoryCustom;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom{
	
	Optional<Product> findByIdAndExpiredAtIsNull(Long id);
	
	List<Product> findByStoreAndPriceControlTypeAndExpiredAtIsNull(Store store, PriceControlType type);
	
	List<Product> findByStoreAndExpiredAtIsNull(Store store);
}
