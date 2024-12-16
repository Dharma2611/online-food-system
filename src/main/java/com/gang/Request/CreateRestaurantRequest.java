package com.gang.Request;

import java.util.List;

import com.gang.Entity.Address;
import com.gang.Entity.ContactInformation;

import lombok.Data;

@Data
public class CreateRestaurantRequest {
	private Long id;
	private String name;
	private String description;
	private String cuisineType;
	private Address address;
	private ContactInformation contactInformation;
	private String openingHours;
	private List<String >images;
	
	
	
	
	
	

}
