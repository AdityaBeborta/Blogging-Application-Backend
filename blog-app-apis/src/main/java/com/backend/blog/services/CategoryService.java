package com.backend.blog.services;

import java.util.List;

import com.backend.blog.payloads.CategoryDto;

public interface CategoryService {

	// create category api
	CategoryDto addcategory(CategoryDto categoryDto);

	// update category api
	CategoryDto updateCategoryByCategoryId(CategoryDto categoryRepo, Integer categoryId);

	// delete category
	void deleteCategoryBycategoryId(Integer categoryId);

	// get all category
	List<CategoryDto> getAllCategory();

	// get a single category
	CategoryDto getASingleCategoryById(Integer categoryId);
}
