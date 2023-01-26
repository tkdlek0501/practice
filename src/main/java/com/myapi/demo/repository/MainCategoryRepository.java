package com.myapi.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapi.demo.domain.MainCategory;

public interface MainCategoryRepository extends JpaRepository<MainCategory, Long>{

}
