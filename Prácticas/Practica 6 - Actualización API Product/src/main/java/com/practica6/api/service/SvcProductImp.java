package com.practica6.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import com.practica6.api.dto.ApiResponse;
import com.practica6.api.dto.DtoProductList;
import com.practica6.api.entity.Category;
import com.practica6.api.entity.Product;
import com.practica6.api.repository.RepoCategory;
import com.practica6.api.repository.RepoProduct;
import com.practica6.api.repository.RepoProductList;
import com.practica6.exception.ApiException;

@Service
public class SvcProductImp implements SvcProduct {

	@Autowired
	RepoProduct repo;

	@Autowired
	RepoProductList repoProductList;

	@Autowired
	RepoCategory repoCategory;

	@Override
	public List<DtoProductList> getProductById(Integer id) {
		List<DtoProductList>  product = repoProductList.findByStatusAndId(1, id);
		if (product == null)
			throw new ApiException(HttpStatus.NOT_FOUND, "product does not exist");
		else
			return product;
	}

	@Override
	public Product getProduct(String gtin) {
		Product product = repo.findByGtinAndStatus(gtin, 1);
		if (product != null)
			return product;
		else
			throw new ApiException(HttpStatus.NOT_FOUND, "product does not exist");
	}

	@Override
	public ApiResponse createProduct(Product in) {
		Product product = repo.findByGtinAndStatus(in.getGtin(),0);
		if(product != null) {
			updateProduct(in,product.getProduct_id());
			return new ApiResponse("product activated");
		}else {
			try {
				in.setStatus(1);
				repo.save(in);
			}catch (DataIntegrityViolationException e) {
				if (e.getLocalizedMessage().contains("gtin"))
					throw new ApiException(HttpStatus.BAD_REQUEST, "product gtin already exist");
				if (e.getLocalizedMessage().contains("product"))
					throw new ApiException(HttpStatus.BAD_REQUEST, "product name already exist");
			}
			return new ApiResponse("product created");
		}
	}

	@Override
	public ApiResponse updateProduct(Product in, Integer id) {
		try {
			repo.updateProduct(id, in.getGtin(), in.getProduct(), in.getDescription(), in.getPrice(), in.getStock());
		}catch (DataIntegrityViolationException e) {
			if (e.getLocalizedMessage().contains("gtin"))
				throw new ApiException(HttpStatus.BAD_REQUEST, "product gtin already exist");
			if (e.getLocalizedMessage().contains("product"))
				throw new ApiException(HttpStatus.BAD_REQUEST, "product name already exist");
		}
		return new ApiResponse("product updated");
	}

	@Override
	public ApiResponse deleteProduct(Integer id) {
		if (repo.deleteProduct(id) > 0)
			return new ApiResponse("product removed");
		else
			throw new ApiException(HttpStatus.BAD_REQUEST, "product cannot be deleted");
	}

	@Override
	public ApiResponse updateProductCategory(Category category, Integer id) {
		try {
			if (repo.updateProductCategory(category.getCategory_id(), id) > 0) {
				return new ApiResponse("product category updated");
			} else {
				throw new ApiException(HttpStatus.BAD_REQUEST, "product category cannot be updated");
			}
		} catch (DataIntegrityViolationException e) {
			throw new ApiException(HttpStatus.NOT_FOUND, "category not found");
		}
	}
}
