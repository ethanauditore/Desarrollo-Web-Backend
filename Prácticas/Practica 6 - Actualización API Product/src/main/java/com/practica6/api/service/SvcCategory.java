package com.practica6.api.service;

import java.util.List;

import com.practica6.api.dto.ApiResponse;
import com.practica6.api.entity.Category;

public interface SvcCategory {

	public List<Category> getCategories();
	public Category getCategory(Integer id);
	public ApiResponse createCategory(Category in);
	public ApiResponse updateCategory(Category in, Integer id);
	public ApiResponse deleteCategory(Integer id);

}
