package com.gang.Service;

import java.util.List;

//import com.code.Globle.Education.Entity.User;
import com.gang.Entity.Order;
import com.gang.Entity.User;
import com.gang.Request.OrderRequest;

public interface OrderService {
	
	
	
	public Order creatOrder(OrderRequest req ,User user)throws Exception;
	
	public Order updateOrder(Long orderId, String orderStatus)throws Exception;
	 void cancelOrder(Long orderId) throws Exception;
	 public List<Order> getUsersOrder(long userid) throws Exception;
	 public List<Order> getRestaurantOrder(Long restaurantId,String orderStatus) throws Exception;
	 
	 public Order findByOrderById(Long orderId) throws Exception;

}
