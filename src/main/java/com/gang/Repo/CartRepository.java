package com.gang.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gang.Entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{
	Cart findByCustomerId(Long userId);

}
