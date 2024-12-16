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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gang.Entity.Food;
import com.gang.Entity.Restaurant;
import com.gang.Entity.User;
import com.gang.Request.CreateFoodRequest;
import com.gang.Service.FoodService;
import com.gang.Service.RestaurantService;
import com.gang.Service.UserService;
import com.gang.response.MessageResponse;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {
	@Autowired
	private FoodService foodService;
	@Autowired
	private UserService userService;
	@Autowired
	private RestaurantService restaurantService;
	
	@PostMapping
	public ResponseEntity<Food> createFood( @RequestBody CreateFoodRequest request , @RequestHeader("authorization") String jwt) throws Exception{
		
		
		User user = userService.findUserByJwtToken(jwt);
		Restaurant Restaurant = restaurantService.findByRestaurantId(request.getRestaurantId());
		Food food = foodService.createFood(request, request.getCategory(), Restaurant);
		
		return new ResponseEntity<Food>(food,HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> deleteFood( @PathVariable Long id, @RequestHeader("authorization") String jwt) throws Exception{
		
		
		User user = userService.findUserByJwtToken(jwt);
		
		foodService.deleteFood(id);
		MessageResponse res = new MessageResponse();
		res.setMessgae("food is delete successfully");
		
		return new ResponseEntity<>(res,HttpStatus.OK);
		
	}
	@PutMapping("/{id}")
	public ResponseEntity<Food> updateAvalabelityStatus( @PathVariable  Long id, @RequestHeader("authorization") String jwt) throws Exception{
		
		
		User user = userService.findUserByJwtToken(jwt);
		
		Food updateAvalablityStatus = foodService.updateAvalablityStatus(id);
		
		return new ResponseEntity<>(updateAvalablityStatus,HttpStatus.OK);
		
	}

}
