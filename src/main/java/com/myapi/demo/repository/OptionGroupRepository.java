package com.myapi.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapi.demo.domain.OptionGroup;
import com.myapi.demo.domain.Product;

public interface OptionGroupRepository extends JpaRepository<OptionGroup, Long>{
	
	Optional<OptionGroup> findByIdAndExpiredAtIsNull(Long id);
	
	List<OptionGroup> findAllByProductAndExpiredAtIsNull(Product product);
}
