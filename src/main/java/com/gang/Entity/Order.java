package com.gang.Entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`order`")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne

	private User customer;

	@JsonIgnore
	@ManyToOne

	private Restaurant restaurant;

	private Long totalAmount;

	private String orderStatus;
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime createdAt;

	@ManyToOne

	private Address deliveryAddress;
//  @JsonIgnore
	@OneToMany( cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
	private List<OrderItem> items;
	@JoinColumn(name = "order_id")
	private int totalItem;

	private Long totalPrice;
}