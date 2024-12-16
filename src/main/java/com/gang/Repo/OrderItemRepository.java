package com.gang.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gang.Entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
