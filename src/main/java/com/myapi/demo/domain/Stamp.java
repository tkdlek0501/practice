package com.myapi.demo.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;

@Getter
@Entity
public class Stamp extends AuditEntity{
	
	private String name;
}
