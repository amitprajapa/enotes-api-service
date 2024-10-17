package com.enotes.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
		List<Category> categories = categoryRepository.findByIsDeletedFalse();
		
		List<CategoryDto> categoriesList = categories.stream().map(cat->mapper.map(cat,  CategoryDto.class)).toList();
		return categoriesList;
	}

	@Override
	public List<CategoryResponse> getActiveCategory() {
		List<Category> all = categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
		List<CategoryResponse> list = all.stream().map(cat->mapper.map(cat, CategoryResponse.class)).toList();
		return list;
	}

	@Override
	public CategoryDto getCategoryById(Integer id) {
		Optional<Category> finOptional = categoryRepository.findByIdAndIsDeletedFalse(id);
		if(finOptional.isPresent()) {
			Category category = finOptional.get();
			return mapper.map(category, CategoryDto.class);
		}
		return null;
	}

	@Override
	public Boolean deleteCatogeryById(Integer id) {
		try {
			Optional<Category> finOptional = categoryRepository.findById(id);
			if(finOptional.isPresent()) {
				Category category = finOptional.get();
				category.setIsDeleted(true);
				categoryRepository.save(category);
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return null;
	}

	@Override
	public List<CategoryDto> findByIsDeletedFalse() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
