package com.myapi.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapi.demo.domain.Store;

public interface StoreRepository extends JpaRepository<Store, Long>{

}
