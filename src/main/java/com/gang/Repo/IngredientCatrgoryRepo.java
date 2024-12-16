package com.gang.Repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.gang.Entity.IngredientCategory;

public interface IngredientCatrgoryRepo extends JpaRepository<IngredientCategory, Long> {
    List<IngredientCategory> findByRestaurantId(Long id); // Use findByRestaurant_Id
}
