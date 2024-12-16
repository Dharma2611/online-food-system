package com.gang.Repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gang.Entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	List<Order> findByCustomerId(Long userId);
	List<Order> findByRestaurantId(Long restaurnatId);

}
