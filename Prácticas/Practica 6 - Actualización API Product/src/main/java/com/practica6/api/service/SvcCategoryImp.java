package com.practica6.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.practica6.api.dto.ApiResponse;
import com.practica6.api.entity.Category;
import com.practica6.api.repository.RepoCategory;
import com.practica6.exception.ApiException;

@Service
public class SvcCategoryImp implements SvcCategory {

	@Autowired
	RepoCategory repo;
	
	@Override
	public List<Category> getCategories() {
		return repo.findByStatus(1);
	}

	@Override
	public Category getCategory(Integer id) {
		Category category = repo.findByCategoryId(id);
		if (category != null)
			return category;
		else
			throw new ApiException(HttpStatus.NOT_FOUND, "category does not exist");
	}

	@Override
	public ApiResponse createCategory(Category category) {
		Category categorySaved = repo.findByCategory(category.getCategory());
		if (categorySaved != null) {
			if (categorySaved.getStatus() == 0) {
				repo.activateCategory(categorySaved.getCategory_id());
				return new ApiResponse("category has been activated");
			}
			else
				throw new ApiException(HttpStatus.BAD_REQUEST,"category alredy exists");
		}
		repo.createCategory(category.getCategory());
		return new ApiResponse("category created");
	}

	@Override
	public ApiResponse updateCategory(Category category, Integer id) {
		Category categorySaved = repo.findByCategoryId(id);
		if(categorySaved == null)
			throw new ApiException(HttpStatus.NOT_FOUND, "category does not exist");
		else {
			if(categorySaved.getStatus() == 0)
				throw new ApiException(HttpStatus.BAD_REQUEST, "category is not active");
			else {
				categorySaved = repo.findByCategory(category.getCategory());
				if(categorySaved != null)
					throw new ApiException(HttpStatus.BAD_REQUEST, "category alredy exists");
				repo.updateCategory(id, category.getCategory());
				return new ApiResponse("category updated");
			}
		}
	}

	@Override
	public ApiResponse deleteCategory(Integer id) {
		Category categorySaved = repo.findByCategoryId(id);
		if (categorySaved == null)
			throw new ApiException(HttpStatus.NOT_FOUND, "category does not exist");
		else {
			repo.deleteById(id);
			return new ApiResponse("category removed");
		}
	}

}
