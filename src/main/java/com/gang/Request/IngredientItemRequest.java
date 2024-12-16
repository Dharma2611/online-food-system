package com.gang.Request;

import lombok.Data;

@Data
public class IngredientItemRequest {
    private Long restaurantId;
	 private Long categoryId;
		private String ingredientName;
//	    private Long restaurantId;

}
