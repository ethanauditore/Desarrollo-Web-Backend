package com.practica6.api.service;

import java.util.List;

import com.practica6.api.dto.ApiResponse;
import com.practica6.api.dto.DtoProductList;
import com.practica6.api.entity.Category;
import com.practica6.api.entity.Product;

public interface SvcProduct {

	public List<DtoProductList> getProductById(Integer id);
	public Product getProduct(String gtin);
	public ApiResponse createProduct(Product in);
	public ApiResponse updateProduct(Product in, Integer id);
	public ApiResponse deleteProduct(Integer id);

	public ApiResponse updateProductCategory(Category category, Integer id);
}
