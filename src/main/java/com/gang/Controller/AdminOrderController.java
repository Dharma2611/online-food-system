package com.gang.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gang.Entity.Order;
import com.gang.Entity.User;
import com.gang.Service.OrderService;
import com.gang.Service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;

	@GetMapping("/order/restaurant/{id}")
	public ResponseEntity<List<Order>> getOrderHistory(@PathVariable Long id,
			@RequestParam(required = false) String order_Status, @RequestHeader("Authorization") String jwt)
			throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		List<Order> orders = orderService.getRestaurantOrder(id, order_Status);
		return new ResponseEntity<>(orders, HttpStatus.CREATED);

	}

	@PutMapping("/order/{id}/{orderStatus}")
	public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id,
			@PathVariable String orderStatus, @RequestHeader("Authorization") String jwt)
			throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		 Order updateOrder = orderService.updateOrder(id, orderStatus);
		return new ResponseEntity<>(updateOrder, HttpStatus.CREATED);

	}

}
