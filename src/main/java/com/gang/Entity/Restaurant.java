package com.gang.Entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@OneToOne
	private User owner;
	private String name;
	private String description;
	private String cuisineType;
	@OneToOne
    @JoinColumn(name = "address_id")
	private Address address;
	@Embedded
	private ContactInformation contactInformation;
	private String openingHours;
	@JsonIgnore
	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Order> orders = new ArrayList<>();
	@Column(length = 1000)
	private List<String> images;
	private LocalDateTime registrationDate;
	private boolean open;
	
	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL ,fetch = FetchType.EAGER)
  @JsonIgnore
	private List<Food> foods = new ArrayList<Food>();
}
