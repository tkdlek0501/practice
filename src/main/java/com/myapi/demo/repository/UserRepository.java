package com.myapi.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapi.demo.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findOneByUsername(String username);
}
