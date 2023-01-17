package com.backend.blog.controllers;

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

import com.backend.blog.payloads.ApiResponse;
import com.backend.blog.payloads.CategoryDto;
import com.backend.blog.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	// create category
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto addedCategory = this.categoryService.addcategory(categoryDto);
		return new ResponseEntity<CategoryDto>(addedCategory, HttpStatus.CREATED);
	}

	// update category
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable Integer categoryId) {
		CategoryDto updateCategoryByCategoryId = this.categoryService.updateCategoryByCategoryId(categoryDto,
				categoryId);
		return ResponseEntity.ok(updateCategoryByCategoryId);
	}

	// delete category
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId) {
		this.categoryService.deleteCategoryBycategoryId(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("deleted successfuly", true), HttpStatus.OK);
	}

	// get a single category
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> singleCategory(@PathVariable Integer categoryId) {
		CategoryDto aSingleCategoryById = this.categoryService.getASingleCategoryById(categoryId);
		return new ResponseEntity<CategoryDto>(aSingleCategoryById, HttpStatus.OK);
	}

	// get All categories
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAll() {
		List<CategoryDto> allCategory = this.categoryService.getAllCategory();
		return new ResponseEntity<List<CategoryDto>>(allCategory, HttpStatus.OK);
	}
}
