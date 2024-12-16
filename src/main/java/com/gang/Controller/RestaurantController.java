package com.gang.Controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gang.Dto.RestaurantDto;
import com.gang.Entity.Restaurant;
import com.gang.Entity.User;
import com.gang.Request.CreateRestaurantRequest;
import com.gang.Service.RestaurantService;
import com.gang.Service.UserService;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

	@Autowired
	private RestaurantService restaurantService;
	@Autowired
	private UserService userService;

	@GetMapping("/search")
	public ResponseEntity<List<Restaurant>> searchRetaurants(@RequestHeader("authorization") String jwt,
			@RequestParam String keyword) throws Exception {

		List<Restaurant> restaurant = restaurantService.searchRestaurant(keyword);
		return new ResponseEntity<List<Restaurant>>(restaurant, HttpStatus.OK);

	}

	@GetMapping()
	public ResponseEntity<List<Restaurant>> getAllRestaurant(@RequestHeader("authorization") String jwt)
			throws Exception {

		List<Restaurant> restaurant = restaurantService.getAllRestaurant();
		return new ResponseEntity<List<Restaurant>>(restaurant, HttpStatus.OK);

	}
	@GetMapping("/{id}")
	public ResponseEntity<Restaurant> getRestaurantById(@RequestHeader("authorization") String jwt ,@PathVariable long id)
			throws Exception {

		Restaurant restaurant = restaurantService.findByRestaurantId(id);
		System.out.println("restaurant id "+restaurant.getId());
		return new ResponseEntity<>(restaurant, HttpStatus.OK);

	}
	@PutMapping("/{id}/add-favorites")
	public ResponseEntity<RestaurantDto> addToFevorite(@RequestHeader("authorization") String jwt ,@PathVariable long id)
			throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		

		RestaurantDto restaurant = restaurantService.addToFavorites(id, user);
		return new ResponseEntity<>(restaurant, HttpStatus.OK);

	}

}
