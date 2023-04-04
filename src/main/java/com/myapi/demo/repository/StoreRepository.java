package com.myapi.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapi.demo.domain.Store;
import com.myapi.demo.domain.User;

public interface StoreRepository extends JpaRepository<Store, Long>{
	
	Optional<Store> findByUser(User user);
	
}
