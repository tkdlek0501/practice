package com.myapi.demo.domain;

import javax.persistence.Entity;

import lombok.Getter;

@Getter
@Entity
public class Delivery extends AuditEntity{
	
	private String name;
}
