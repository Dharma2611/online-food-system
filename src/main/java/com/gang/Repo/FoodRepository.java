package com.gang.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gang.Entity.Food;

public interface FoodRepository extends JpaRepository<Food, Long>{
	
	
//	List<Food> findRestaurantId(Long restaurantId);
	  List<Food> findByRestaurantId(Long restaurantId);
	@Query("SELECT f FROM Food f WHERE f.name LIKE CONCAT('%', :keyword, '%') OR f.foodCategory.name LIKE CONCAT('%', :keyword, '%')")

	List<Food> searchFood(@Param ("keyword") String keyword);
	Food findFoodById(Long foodId);
}
