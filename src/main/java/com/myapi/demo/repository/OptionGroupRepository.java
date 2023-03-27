package com.myapi.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapi.demo.domain.OptionGroup;

public interface OptionGroupRepository extends JpaRepository<OptionGroup, Long>{
	
	Optional<OptionGroup> findByIdAndExpiredAtIsNull(Long id);

}
