package com.practica6.api.repository;

import java.util.List;

import com.practica6.api.dto.DtoProductList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoProductList extends JpaRepository<DtoProductList, Integer> {

	@Query(value = "SELECT * FROM product WHERE product_id = :product_id AND status = :status", nativeQuery = true)
    List<DtoProductList> findByStatusAndId(@Param("status") Integer status, @Param("product_id") Integer product_id);
}
