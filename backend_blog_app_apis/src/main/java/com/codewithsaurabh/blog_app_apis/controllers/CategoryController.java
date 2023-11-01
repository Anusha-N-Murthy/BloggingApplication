package com.codewithsaurabh.blog_app_apis.controllers;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.codewithsaurabh.blog_app_apis.payloads.ApiResponse;
import com.codewithsaurabh.blog_app_apis.payloads.CategoryDto;
import com.codewithsaurabh.blog_app_apis.services.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

	// POST-create Category
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto CategoryDto) {
		CategoryDto createCategoryDto = this.categoryService.createCategory(CategoryDto);
		return new ResponseEntity<>(createCategoryDto, HttpStatus.CREATED);
	}

//	PUT-update Category
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto CategoryDto,
			@PathVariable("categoryId") Integer categoryId) {
		CategoryDto updateCategory = this.categoryService.updateCategory(CategoryDto, categoryId);
		return ResponseEntity.ok(updateCategory);
	}

//	DELETE-delete Category
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer categoryId) {
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully!", true), HttpStatus.OK);
	}

//	GET-Category-Single
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable Integer categoryId) {
		return ResponseEntity.ok(this.categoryService.getCategoryById(categoryId));
	}

//	GET-Category All
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategorys() {
		return ResponseEntity.ok(this.categoryService.getAllCategory());
	}
}