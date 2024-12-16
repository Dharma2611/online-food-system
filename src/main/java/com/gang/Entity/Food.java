package com.gang.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Food {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String description;
	private Long price;
	@ManyToOne
	private  Category foodCategory;
	@Column(length = 1000)
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> images;
	private boolean available;
	@ManyToOne
	private Restaurant restaurant;
	private boolean isVegetarian;
	private boolean isSeasonal;
	@ManyToMany(fetch = FetchType.EAGER)
	private List<IngredientsItems> ingredientsItems= new ArrayList<>();
	private Date creationDate;

}