package com.gang.Request;

import com.gang.Entity.Address;

import lombok.Data;
@Data
public class OrderRequest {
	
	private Long restaurantId;
	private Address deliveryAddress;

}
