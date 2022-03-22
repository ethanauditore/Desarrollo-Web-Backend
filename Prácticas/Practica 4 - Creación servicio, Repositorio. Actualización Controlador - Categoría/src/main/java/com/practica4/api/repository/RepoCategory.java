package com.practica4.api.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.practica4.api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoCategory extends JpaRepository<Category, Integer> {

  @Query(value = "SELECT * FROM category WHERE status = :status", nativeQuery = true)
  public List<Category> findByStatus(@Param("status") Integer status);

  @Query(value = "SELECT * FROM category WHERE category_id = :category_id AND status = 1", nativeQuery = true)
  public Category findByCategoryId(@Param("category_id") Integer category_id);

  @Query(value = "SELECT * FROM category WHERE category = :category", nativeQuery = true)
  public Category findByCategory(@Param("category") String category);

  @Modifying
  @Transactional
  @Query(value = "INSERT INTO category (category, status) VALUES (:category, 1)", nativeQuery = true)
  public void createCategory(@Param("category") String category);

  @Modifying
  @Transactional
  @Query(value = "UPDATE category SET category = :category WHERE category_id = :category_id", nativeQuery = true)
  public Integer updateCategory(@Param("category_id") Integer category_id, @Param("category") String category);

  @Modifying
  @Transactional
  @Query(value = "UPDATE category SET status = 1 WHERE category_id = :category_id", nativeQuery = true)
  public Integer activateCategory(@Param("category_id") Integer category_id);

  @Modifying
  @Transactional
  @Query(value = "UPDATE category SET status = 0 WHERE category_id = :category_id", nativeQuery = true)
  public void deleteById(@Param("category_id") Integer category_id);
}
