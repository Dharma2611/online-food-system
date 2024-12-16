package com.gang.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gang.Entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	List<Category> findByRestaurantId(Long id);

}
