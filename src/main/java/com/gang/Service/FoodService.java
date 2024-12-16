package com.gang.Service;

import java.util.List;

import com.gang.Entity.Category;
import com.gang.Entity.Food;
import com.gang.Entity.Restaurant;
import com.gang.Request.CreateFoodRequest;

public interface FoodService {

	public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant) throws Exception;

	void deleteFood(Long foodId) throws Exception;

	public List<Food> getRestaurantFood(Long restaurantId, boolean isVegetarian, boolean isNonVege, boolean isSeasonal,
			String foodCategory) throws Exception;

	public List<Food> search(String keyword) throws Exception;

	public Food findFoodById(Long foodId) throws Exception;

	public Food updateAvalablityStatus(Long foodId) throws Exception;

}
