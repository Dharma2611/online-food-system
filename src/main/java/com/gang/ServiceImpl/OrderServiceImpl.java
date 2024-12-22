package com.gang.ServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gang.Entity.Address;
import com.gang.Entity.Cart;
import com.gang.Entity.CartItems;
import com.gang.Entity.Order;
import com.gang.Entity.OrderItem;
import com.gang.Entity.Restaurant;
import com.gang.Entity.User;
import com.gang.Repo.AddressRpository;
import com.gang.Repo.OrderItemRepository;
import com.gang.Repo.OrderRepository;
import com.gang.Repo.UserRepository;
import com.gang.Request.OrderRequest;
import com.gang.Service.CartService;
import com.gang.Service.OrderService;
import com.gang.Service.RestaurantService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderItemRepository orderItemRepository;
	@Autowired
	private AddressRpository addressRpository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RestaurantService restaurantService;
	@Autowired
	private CartService cartService;

	@Override
	public Order creatOrder(OrderRequest req, User user) throws Exception {
		Address shipedAddress = req.getDeliveryAddress();
		Address saveAddress = addressRpository.save(shipedAddress);
	
		if (!user.getAddress().contains(saveAddress)) {
			user.getAddress().add(saveAddress);
			userRepository.save(user);
		}
		Restaurant restaurant = restaurantService.findByRestaurantId(req.getRestaurantId());
		Order order = new Order();
	
		
		order.setCustomer(user);
		order.setCreatedAt(LocalDateTime.now());
		order.setOrderStatus("PENDING");
		order.setDeliveryAddress(saveAddress);
		order.setRestaurant(restaurant);

		Cart cart = cartService.findCardByUserId(user.getId());
		List<OrderItem> orderItems = new ArrayList<>();
		   for (CartItems cartItems : cart.getItems()) {
		        OrderItem orderItem = new OrderItem();
		        orderItem.setFood(cartItems.getFood());
		        orderItem.setIngredients(cartItems.getIngredients());
		        orderItem.setQuantity(cartItems.getQuantity());
		        orderItem.setTotalPrice(cartItems.getTotalPrice());
//		        orderItem.setOrder(order); // Explicitly associate the order
		        orderItems.add(orderItem);
		    }
		Long total = cartService.calculateCartTotal(cart); 
		order.setItems(orderItems);
		order.setTotalPrice(total);
		Order save2 = orderRepository.save(order);
		restaurant.getOrders().add(save2);

		return order;
	}

	@Override
	public Order updateOrder(Long orderId, String orderStatus) throws Exception {
		// TODO Auto-generated method stub
		Order byOrderById = findByOrderById(orderId);
		if (orderStatus.equals("OUT_FOR_DELIVERY") || orderStatus.equals("DELIVERED") || orderStatus.equals("PENDING")
				|| orderStatus.equals("COMPLETED")) {
			byOrderById.setOrderStatus(orderStatus);
			return orderRepository.save(byOrderById);
		}
		throw new Exception("Please select a valid Status");

	}

	@Override
	public void cancelOrder(Long orderId) throws Exception {
		Order order = findByOrderById(orderId);
		orderRepository.deleteById(orderId);
	}

	@Override
	@Transactional
	public List<Order> getUsersOrder(long userId) throws Exception {
		
		return orderRepository.findAllUserOrders(userId);
	}

	@Override
	public List<Order> getRestaurantOrder(Long restaurantId, String orderStatus) throws Exception {
		// TODO Auto-generated method stub
		List<Order> orders = orderRepository.findOrdersByRestaurantId(restaurantId);
		if(orderStatus!=null) {
			List<Order> collect = orders.stream().filter(order->order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
			
		}
		
		return orders;
	}

	@Override
	public Order findByOrderById(Long orderId) throws Exception {

		Optional<Order> byId = orderRepository.findById(orderId);
		if (byId.isEmpty()) {
			throw new Exception(" order Not Found ");
		}
		// TODO Auto-generated method stub
		return byId.get();
	}

}
