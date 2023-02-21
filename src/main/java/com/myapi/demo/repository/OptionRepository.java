package com.myapi.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapi.demo.domain.Option;

public interface OptionRepository extends JpaRepository<Option, Long>{

}
