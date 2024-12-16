package com.gang.Service;

import java.util.List;

import com.gang.Entity.IngredientCategory;
import com.gang.Entity.IngredientsItems;

public interface IngredientService {
	
	
	IngredientCategory createIngredientCategory(String name,long restaurantId)throws Exception;
	IngredientCategory findIngredientCategoryById(Long id)throws Exception;
	List<IngredientCategory> findIngredientCategoryByRestaurnatId(Long id)throws Exception;
	IngredientsItems createIngredientItem(Long restaurantId, String intgredientName,Long categoryId) throws Exception;
	List<IngredientsItems> findRestaurantIngredients(Long restaurantId)throws Exception;
	IngredientsItems updateStock(Long id)throws Exception;

}
