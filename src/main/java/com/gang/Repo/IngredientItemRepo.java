package com.gang.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gang.Entity.IngredientsItems;

public interface IngredientItemRepo extends JpaRepository<IngredientsItems, Long> {

	List<IngredientsItems> findByRestaurantId(Long restaurantId);

}
