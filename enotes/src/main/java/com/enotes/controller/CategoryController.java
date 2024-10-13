package com.enotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.dto.CategoryDto;
import com.enotes.dto.CategoryResponse;
import com.enotes.entity.Category;
import com.enotes.service.CategoryService;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/save-category")
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDto category){
		try {
			String name = category.getName();
			String desc = category.getDescription();
			if(name.isEmpty()) {
				return new ResponseEntity<>("name can not be empty",HttpStatus.INTERNAL_SERVER_ERROR);
			}
			if(desc.isEmpty()) {
				return new ResponseEntity<>("description can not be empty",HttpStatus.INTERNAL_SERVER_ERROR);
			}
		Boolean b = categoryService.saveCategory(category);
		if (b) {
			return new ResponseEntity<>("saved sucess",HttpStatus.CREATED); 
		} else {
			return new ResponseEntity<>("not saved",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	@GetMapping("/category")
	public ResponseEntity<?> getAllCategory(@RequestBody Category category){
		List<CategoryDto> allCategory = categoryService.getAllCategory();
		if(CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		} else {
			return new ResponseEntity<>(allCategory, HttpStatus.OK);
		}
		
	}
	
	@GetMapping("/active-category")
	public ResponseEntity<?> getActiveCategory(){
		List<CategoryResponse> allCategory = categoryService.getActiveCategory();
		if(CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		} else {
			return new ResponseEntity<>(allCategory, HttpStatus.OK);
		}
		
	}

}
