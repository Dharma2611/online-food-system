package com.gang.Service;

import java.util.List;

import com.gang.Dto.RestaurantDto;
import com.gang.Entity.Restaurant;
import com.gang.Entity.User;
import com.gang.Request.CreateRestaurantRequest;

public interface RestaurantService {
	public Restaurant createRestaurant(CreateRestaurantRequest rRequest ,User user)throws Exception;
	public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatRestaurantRequest)throws Exception;
	public void deleteRestaurant(Long restaurantId)throws Exception;
	public List<Restaurant> getAllRestaurant();
	public List<Restaurant> searchRestaurant(String keyword);
	public Restaurant findByRestaurantId(long id) throws Exception;
	public Restaurant getRestaurantByUserId(long userId) throws Exception;
	public RestaurantDto addToFavorites(long restaurantId , User user)throws Exception;
	public Restaurant updateRestaurantStatus(long id) throws Exception;
	

}
