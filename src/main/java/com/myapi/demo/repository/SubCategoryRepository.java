package com.myapi.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapi.demo.domain.SubCategory;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long>{

}
