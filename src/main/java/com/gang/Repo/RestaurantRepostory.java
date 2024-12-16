package com.gang.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gang.Entity.Restaurant;

public interface RestaurantRepostory extends JpaRepository<Restaurant, Long>{
	@Query("SELECT r FROM Restaurant r WHERE lower(r.name) LIKE lower(concat('%',:query , '%')) OR lower(r.cuisineType) LIKE lower(concat('%',:query,'%')) ")
	List<Restaurant> findBySearchQuery(String query);
	Restaurant findByOwnerId(long ownerId);
	
	

}
