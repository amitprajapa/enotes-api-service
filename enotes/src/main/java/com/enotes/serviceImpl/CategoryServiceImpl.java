package com.enotes.serviceImpl;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enotes.dto.CategoryDto;
import com.enotes.dto.CategoryResponse;
import com.enotes.entity.Category;
import com.enotes.repository.CategoryRepository;
import com.enotes.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public Boolean saveCategory(CategoryDto categoryDto) {
		
//		Category category = new Category();
//		category.setName(categoryDto.getName());
//		category.setDescription(categoryDto.getDescription());
//		category.setIsActive(categoryDto.getIsActive());
		
		Category category = mapper.map(categoryDto, Category.class);
		
		category.setIsDeleted(false);
		category.setCreatedBy(1);
		category.setCreatedOn(new Date());
		Category save = categoryRepository.save(category);
		if(ObjectUtils.isEmpty(save)) {
			return false;
		}
		return true;
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> categories = categoryRepository.findAll();
		
		List<CategoryDto> categoriesList = categories.stream().map(cat->mapper.map(cat,  CategoryDto.class)).toList();
		return categoriesList;
	}

	@Override
	public List<CategoryResponse> getActiveCategory() {
		List<Category> all = categoryRepository.findByIsActiveTrue();
		List<CategoryResponse> list = all.stream().map(cat->mapper.map(cat, CategoryResponse.class)).toList();
		return list;
	}
	
	

}
