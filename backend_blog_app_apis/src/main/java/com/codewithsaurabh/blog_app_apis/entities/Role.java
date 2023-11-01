package com.codewithsaurabh.blog_app_apis.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class Role {
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int roleId;
	
	private String name;
}
