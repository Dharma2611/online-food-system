package com.gang.Request;

import lombok.Data;

@Data
public class UpdateCartRequest {
	private Long cartItemId;

	private int quantity;

}
