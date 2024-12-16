package com.gang.ServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gang.Entity.Category;
import com.gang.Entity.Food;
import com.gang.Entity.Restaurant;
import com.gang.Repo.FoodRepository;
import com.gang.Request.CreateFoodRequest;
import com.gang.Service.FoodService;

import jakarta.transaction.Transactional;
@Service
@Transactional
public class FoodServiceImpl implements FoodService {

	@Autowired
	private FoodRepository frepository;

	@Override
	public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant) throws Exception {
		Food food = new Food();
		food.setFoodCategory(category);
		food.setRestaurant(restaurant);
		food.setName(req.getName());
		food.setDescription(req.getDescription());
		food.setImages(req.getImages());
		food.setPrice(req.getPrice());
		food.setIngredientsItems(req.getIngredients());
		food.setSeasonal(req.isSeasonal());
		food.setVegetarian(req.isVegetarian());
		Food saveFood = frepository.save(food);
		restaurant.getFoods().add(saveFood);

		return saveFood;
	}

	@Override
	public void deleteFood(Long foodId) throws Exception {
		Food foodById = findFoodById(foodId);
		foodById.setRestaurant(null);
		frepository.save(foodById);

	}

	@Override
	public List<Food> getRestaurantFood(Long restaurantId, boolean isVegetarian, boolean isNonVege, boolean isSeasonal,
			String foodCategory) throws Exception {
		List<Food> restaurantList = frepository.findByRestaurantId(restaurantId);
		if (isNonVege) {
			restaurantList = FilterByNonVege(restaurantList, isNonVege);
		}
		if (isVegetarian) {
			restaurantList = FilterByVegetarian(restaurantList, isVegetarian);
		}
		if (isSeasonal) {
			restaurantList = FilterBySeasonal(restaurantList, isSeasonal);
		}
		if (foodCategory != null && !foodCategory.equals("")) {
			restaurantList = FilterByCatgory(restaurantList, foodCategory);
		}
		return restaurantList;
	}

	private List<Food> FilterByCatgory(List<Food> restaurantList, String foodCategory) {
		return restaurantList.stream().filter(food->{
			if(food.getFoodCategory()!=null) {
				return food.getFoodCategory().getName().equals(foodCategory);
				
			}
			else {
				return false;
			}
		}).collect(Collectors.toList());

	}

	private List<Food> FilterByVegetarian(List<Food> restaurantList, boolean isVegetarian) {

		return restaurantList.stream().filter(food -> food.isVegetarian() == isVegetarian).collect(Collectors.toList());
	}

	private List<Food> FilterBySeasonal(List<Food> restaurantList, boolean isSeasonal) {

		return restaurantList.stream().filter(food -> food.isVegetarian() == isSeasonal).collect(Collectors.toList());
	}

	private List<Food> FilterByNonVege(List<Food> restaurantList, boolean isNonVege) {

		return restaurantList.stream().filter(food -> food.isVegetarian() == false).collect(Collectors.toList());
	}

	@Override
	public List<Food> search(String keyword) throws Exception {

		return frepository.searchFood(keyword);
	}

	@Override
	public Food findFoodById(Long foodId) throws Exception {
		
		Optional<Food> byId = frepository.findById(foodId);
		if(byId.isEmpty()) {
			throw new Exception(" food is not exist....");
		}

		return byId.get();
	}

	@Override
	public Food updateAvalablityStatus(Long foodId) throws Exception {
		Food food = frepository.findFoodById(foodId);
		food.setAvailable(!food.isAvailable());

		return frepository.save(food);
	}

}
