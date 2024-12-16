package com.gang.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gang.Entity.IngredientCategory;
import com.gang.Entity.IngredientsItems;
import com.gang.Request.IngredientCategoryRequest;
import com.gang.Request.IngredientItemRequest;
import com.gang.Service.IngredientService;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {
	@Autowired

	private IngredientService ingredientService;

	@PostMapping("/category")
	public ResponseEntity<IngredientCategory> createIngredientCategory(@RequestBody IngredientCategoryRequest req)
			throws Exception {

		IngredientCategory incredient = ingredientService.createIngredientCategory(req.getName(),
				req.getRestaurantId());
		return new ResponseEntity<IngredientCategory>(incredient, HttpStatus.CREATED);

	}

	@PostMapping()
	public ResponseEntity<IngredientsItems> createIngredientItem(@RequestBody IngredientItemRequest req)
			throws Exception {

		IngredientsItems item = ingredientService.createIngredientItem(req.getRestaurantId(), req.getIngredientName(), req.getCategoryId());

		return new ResponseEntity<IngredientsItems>(item, HttpStatus.CREATED);

	}

	@GetMapping("/")
	public ResponseEntity<IngredientCategory> getIngredientCategoryById(@PathVariable Long id) throws Exception {
		IngredientCategory category = ingredientService.findIngredientCategoryById(id);
		return new ResponseEntity<IngredientCategory>(category, HttpStatus.OK);

	}

	@GetMapping("/restaurant/{id}/category")

	public ResponseEntity<List<IngredientCategory>> getIngredientCategoryByRestaurnatId(Long id) throws Exception {
		List<IngredientCategory> ingredientCategoryByRestaurnatId = ingredientService
				.findIngredientCategoryByRestaurnatId(id);
		return new ResponseEntity<List<IngredientCategory>>(ingredientCategoryByRestaurnatId, HttpStatus.OK);

	}

	@GetMapping("/restaurant/{id}")
	public ResponseEntity<List<IngredientsItems>> getRestaurantIngredients(@PathVariable Long id) throws Exception {
		List<IngredientsItems> restaurantIngredients = ingredientService.findRestaurantIngredients(id);
		return new ResponseEntity<List<IngredientsItems>>(restaurantIngredients, HttpStatus.OK);

	}

	@PutMapping("/{id}/stoke")
	public ResponseEntity<IngredientsItems> updateStock(@PathVariable Long id) throws Exception {
		IngredientsItems updateStock = ingredientService.updateStock(id);
		return new ResponseEntity<>(updateStock, HttpStatus.OK);

	}

}
