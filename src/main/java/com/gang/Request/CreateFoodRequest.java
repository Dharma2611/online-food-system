
package com.gang.Request;

import java.util.List;

import com.gang.Entity.Category;
import com.gang.Entity.IngredientsItems;

import lombok.Data;
@Data

public class CreateFoodRequest {
	private String name;
	private String description;
	private Long price;
	private Category category;
	private List<String > images;
	private Long restaurantId;
	private boolean vegetarian;
	private boolean seasonal;
	private List<IngredientsItems>ingredients;
	

}
