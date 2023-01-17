package com.backend.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.blog.entities.Category;
import com.backend.blog.exceptions.ResourceNotFoundException;
import com.backend.blog.payloads.CategoryDto;
import com.backend.blog.repository.CategoryRepo;
import com.backend.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto addcategory(CategoryDto categoryDto) {
		Category category = this.modelMapper.map(categoryDto, Category.class);
		Category savedCategory = this.categoryRepo.save(category);
		return this.modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategoryByCategoryId(CategoryDto categoryDto, Integer categoryId) {
		Category oldCategory = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));
		oldCategory.setCategoryTitle(categoryDto.getCategoryTitle());
		oldCategory.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updatedCategory = this.categoryRepo.save(oldCategory);
		return this.modelMapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategoryBycategoryId(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));
		this.categoryRepo.delete(category);

	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> category = this.categoryRepo.findAll();
		List<CategoryDto> getAllCategory = category.stream().map((cat) -> this.modelMapper.map(cat, CategoryDto.class))
				.collect(Collectors.toList());
		System.out.println(getAllCategory);
		return getAllCategory;
	}

	@Override
	public CategoryDto getASingleCategoryById(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));
		return this.modelMapper.map(category, CategoryDto.class);
	}

}
