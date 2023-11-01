package com.codewithsaurabh.blog_app_apis.services;

import java.util.List;

import com.codewithsaurabh.blog_app_apis.payloads.CategoryDto;

public interface CategoryService {
	// default public abstract
	// create
	CategoryDto createCategory(CategoryDto categoryDto);

	// update
	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

	// delete
	void deleteCategory(Integer categoryId);

	// get
	CategoryDto getCategoryById(Integer categoryId);

	// getAll
	List<CategoryDto> getAllCategory();
}
