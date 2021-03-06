package com.poly.SOF3021.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.SOF3021.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
}
