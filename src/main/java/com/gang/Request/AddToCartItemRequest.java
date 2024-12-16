package com.gang.Request;

import java.util.List;

import lombok.Data;
@Data
public class AddToCartItemRequest {
	
	private Long foodId;
	private int quantity;
	private List<String> ingredients;

}
