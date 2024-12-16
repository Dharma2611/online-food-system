package com.gang.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gang.Entity.Restaurant;
import com.gang.Entity.User;
import com.gang.Request.CreateRestaurantRequest;
import com.gang.Service.RestaurantService;
import com.gang.Service.UserService;
import com.gang.response.MessageResponse;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {

	@Autowired
	private RestaurantService restaurantService;
	@Autowired
	private UserService userService;

	@PostMapping()
	public ResponseEntity<Restaurant> creatRautaurant(@RequestBody CreateRestaurantRequest req,
			@RequestHeader("authorization") String jwt) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		Restaurant restaurant = restaurantService.createRestaurant(req, user);

		return new ResponseEntity<Restaurant>(restaurant, HttpStatus.CREATED);

	}

	@PutMapping("/{id}")
	public ResponseEntity<Restaurant> updateRautaurant(@RequestBody CreateRestaurantRequest req,
			@RequestHeader("authorization") String jwt, @PathVariable long id) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		Restaurant restaurant = restaurantService.updateRestaurant(id, req);

		return new ResponseEntity<Restaurant>(restaurant, HttpStatus.OK);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> deleteRautaurant(@RequestHeader("authorization") String jwt,
			@PathVariable long id) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		restaurantService.deleteRestaurant(id);
		MessageResponse message = new MessageResponse();
		message.setMessgae("restaurant is delete succesfully ");

		return new ResponseEntity<MessageResponse>(message, HttpStatus.OK);

	}

	@PutMapping("/{id}/status")
	public ResponseEntity<Restaurant> updateRautaurantStatus(@RequestHeader("authorization") String jwt,
			@PathVariable long id) throws Exception {

		// Fetch the restaurant by ID
		Restaurant restaurant = restaurantService.getRestaurantByUserId(id);

		// Check if the restaurant exists
		if (restaurant == null) {

			throw new Exception("Restaurant not found");

		}
		Restaurant restaurant1 = restaurantService.updateRestaurantStatus(id);

		return new ResponseEntity<Restaurant>(restaurant1, HttpStatus.OK);

	}

	@GetMapping("/user")
	public ResponseEntity<Restaurant> findRestaurantByUserId(@RequestHeader("authorization") String jwt)
			throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());

		return new ResponseEntity<Restaurant>(restaurant, HttpStatus.OK);

	}

}
