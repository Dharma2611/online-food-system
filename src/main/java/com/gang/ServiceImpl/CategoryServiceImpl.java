package com.gang.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gang.Entity.Category;
import com.gang.Entity.Restaurant;
import com.gang.Repo.CategoryRepository;
import com.gang.Service.CategoryService;
import com.gang.Service.RestaurantService;

import jakarta.transaction.Transactional;
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired 
	private RestaurantService restaurantService;

	@Override
	public Category createCategory(String name, Long userId) throws Exception {
		Restaurant restaurant = restaurantService.getRestaurantByUserId(userId);
		Category category = new Category();
		category.setName(name);
		category.setRestaurant(restaurant);
		
		return categoryRepository.save(category);
	}

	@Override
	public List<Category> findCategoryByRestaurantsId(Long id) throws Exception {
		
		Restaurant byRestaurantId = restaurantService.findByRestaurantId(id);
		
		
		return categoryRepository.findByRestaurantId(byRestaurantId.getId());
	}

	@Override
	public Category findCategoryById(Long id) throws Exception 
	{
		Optional<Category> opCategory = categoryRepository.findById(id);
		if(opCategory.isEmpty()) {
			throw new Exception("category not found....");
		}
		
		
		return opCategory.get();
	}

}
