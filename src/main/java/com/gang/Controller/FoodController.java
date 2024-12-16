package com.gang.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gang.Entity.Food;
import com.gang.Entity.User;
import com.gang.Service.FoodService;
import com.gang.Service.RestaurantService;
import com.gang.Service.UserService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    // Search endpoint
    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(
            @RequestParam String keyword,
            @RequestHeader("authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        List<Food> searchResults = foodService.search(keyword);

        return new ResponseEntity<>(searchResults, HttpStatus.OK);
    }

    // Get restaurant food with filters
    @GetMapping("/restaurant/{restaurantId}")
    @Transactional
    public ResponseEntity<List<Food>> getRestaurantFood(
            @PathVariable Long restaurantId,
            @RequestParam(required = false) Boolean NonVeg,
            @RequestParam(required = false) Boolean Vegetarian,
            @RequestParam(required = false) Boolean Seasonal,
            @RequestParam(required = false) String food_category,
            @RequestHeader("authorization") String jwt) throws Exception {
    	boolean isNonVeg = (NonVeg != null) ? NonVeg : false;

        User user = userService.findUserByJwtToken(jwt);

        List<Food> foods = foodService.getRestaurantFood(restaurantId, Vegetarian, isNonVeg, Seasonal, food_category);

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }
}
