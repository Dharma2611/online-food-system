package com.gang.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gang.Entity.IngredientCategory;
import com.gang.Entity.IngredientsItems;
import com.gang.Entity.Restaurant;
import com.gang.Repo.IngredientCatrgoryRepo;
import com.gang.Repo.IngredientItemRepo;
import com.gang.Service.IngredientService;
import com.gang.Service.RestaurantService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class IngredientServiceImpl implements IngredientService {
	@Autowired

	private RestaurantService restaurantService;
	@Autowired

	private IngredientCatrgoryRepo ingredientCatrgoryRepo;
	@Autowired

	private IngredientItemRepo ingredientItemRepo;

	@Override
	public IngredientCategory createIngredientCategory(String name, long restaurantId) throws Exception {
		Restaurant restaurant = restaurantService.findByRestaurantId(restaurantId);
		System.out.println("restaurant id from restaurant service" + restaurant.getId());

		IngredientCategory ingredientCategory = new IngredientCategory();
		ingredientCategory.setName(name);
		ingredientCategory.setRestaurant(restaurant);

		return ingredientCatrgoryRepo.save(ingredientCategory);
	}

	@Override
	public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
		Optional<IngredientCategory> id1 = ingredientCatrgoryRepo.findById(id);
		if (id1.isEmpty()) {
			throw new Exception("category is not avalabe");
		}

		return id1.get();
	}

	@Override
	public List<IngredientCategory> findIngredientCategoryByRestaurnatId(Long id) throws Exception {
		restaurantService.findByRestaurantId(id);

//		if (byRestaurantId == null) {
//			throw new Exception("id is found");
//		}
		List<IngredientCategory> categoryByRestaurnat = ingredientCatrgoryRepo.findByRestaurantId(id);

		return categoryByRestaurnat;
	}

	@Override
	public IngredientsItems createIngredientItem(Long restaurantId, String intgredientName, Long categoryId)
			throws Exception {
		Restaurant restaurant = restaurantService.findByRestaurantId(restaurantId);
		if (restaurant == null) {
			throw new Exception("Restaurant with ID " + restaurantId + " is not available.");
		}

		// Check if the ingredient category exists
		IngredientCategory category = findIngredientCategoryById(categoryId);
		if (category == null) {
			throw new Exception("Ingredient category with ID " + categoryId + " is not available.");
		}
		IngredientsItems ingredientItem = new IngredientsItems();
		ingredientItem.setIngredientCategory(category);
		ingredientItem.setName(intgredientName);
		ingredientItem.setRestaurant(restaurant);
		IngredientsItems save = ingredientItemRepo.save(ingredientItem);
		category.getIngredients().add(save);

		return save;
	}

	@Override
	public List<IngredientsItems> findRestaurantIngredients(Long restaurantId) throws Exception {
		List<IngredientsItems> byRestaurantId = ingredientItemRepo.findByRestaurantId(restaurantId);

		return byRestaurantId;
	}

	@Override
	public IngredientsItems updateStock(Long id) throws Exception {
		Optional<IngredientsItems> byId = ingredientItemRepo.findById(id);
		if (byId.isEmpty()) {
			throw new Exception("Ingredient item is no avalbe");
		}
		IngredientsItems ingredientsItems = byId.get();
		ingredientsItems.setIsStoke(!ingredientsItems.getIsStoke());

		return ingredientItemRepo.save(ingredientsItems);
	}

}
