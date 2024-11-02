package com.enotes.service;

import java.util.List;

import com.enotes.dto.CategoryDto;
import com.enotes.dto.CategoryResponse;
import com.enotes.entity.Category;
import com.enotes.exception.ResourceNotFoundException;

public interface CategoryService {
	
	public Boolean saveCategory(CategoryDto category);
	public List<CategoryDto> getAllCategory();
	public List<CategoryResponse> getActiveCategory();
	public CategoryDto getCategoryById(Integer id) throws ResourceNotFoundException;
	public Boolean deleteCatogeryById(Integer id);
	public List<CategoryDto> findByIsDeletedFalse();
}
