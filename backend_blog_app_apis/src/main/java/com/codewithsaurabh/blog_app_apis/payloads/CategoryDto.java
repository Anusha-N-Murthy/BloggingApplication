package com.codewithsaurabh.blog_app_apis.payloads;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer categoryId;
	
	@NotBlank
	@Size(min=4, message = "Min size of category title is 4")
	@Column(name = "title", length = 20, nullable = false)
	private String categoryTitle;
	
	@NotBlank
	@Size(min=10, message = "Min size of category title is 10")
	@Column(name = "description")
	private String categoryDiscription;
}
