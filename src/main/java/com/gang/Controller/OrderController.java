package com.gang.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gang.Entity.Order;
import com.gang.Entity.User;
import com.gang.Request.OrderRequest;
import com.gang.Service.OrderService;
import com.gang.Service.UserService;

@RestController
@RequestMapping("/api")

public class OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;
	@PostMapping("/order")
	public ResponseEntity<Order> createOrder(@RequestBody   OrderRequest request,  @RequestHeader("Authorization") String jwt)throws Exception{
		User user = userService.findUserByJwtToken(jwt);
		Order order = orderService.creatOrder(request, user);
		return new ResponseEntity<>(order,HttpStatus.CREATED);
		
	}
	
	@GetMapping("/order/user")
	public ResponseEntity< List<Order>> getOrderHistory( @RequestHeader("Authorization") String jwt)throws Exception{
		User user = userService.findUserByJwtToken(jwt);
	 List<Order> orders = orderService.getUsersOrder(user.getId());
		return new ResponseEntity<>(orders,HttpStatus.CREATED);
		
	}
}
