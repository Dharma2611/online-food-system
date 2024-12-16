 package com.gang.ServiceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gang.Entity.Cart;
import com.gang.Entity.CartItems;
import com.gang.Entity.Food;
import com.gang.Entity.User;
import com.gang.Exception.CartItemNotFoundException;
import com.gang.Repo.CartItemRepository;
import com.gang.Repo.CartRepository;
import com.gang.Request.AddToCartItemRequest;
import com.gang.Service.CartService;
import com.gang.Service.FoodService;
import com.gang.Service.UserService;

import jakarta.transaction.Transactional;
@Service
@Transactional
public class CartServiceImpl  implements CartService{
	
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private CartItemRepository cartItemRepository;
	@Autowired
	private FoodService foodService;
	@Autowired
	private UserService userService;

	@Override
	public CartItems addToCart(AddToCartItemRequest req, String jwt) throws Exception {
		// TODO Auto-generated method stub
		User user = userService.findUserByJwtToken(jwt);
		Food food = foodService.findFoodById(req.getFoodId());
		Cart cart = cartRepository.findByCustomerId(user.getId());
		for(CartItems cartItems : cart.getItems()) {
			int newQuantity = cartItems.getQuantity()+req.getQuantity();
			return updateCartItemQuantity(cartItems.getId(), newQuantity);
		}
		
		CartItems newCartItem= new CartItems();
		
		newCartItem.setFood(food);
		newCartItem.setCart(cart);
		newCartItem.setQuantity(req.getQuantity());
		newCartItem.setIngredients(req.getIngredients());
		newCartItem.setTotalPrice(req.getQuantity()*food.getPrice());
		CartItems save = cartItemRepository.save(newCartItem);
		cart.getItems().add(save);
		return save;
	}

	@Override
	public CartItems updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
		
		Optional<CartItems> cartItem = cartItemRepository.findById(cartItemId);
		if (cartItem.isEmpty()) {
		    throw new CartItemNotFoundException("CartItem is not present with id: " + cartItemId);
		}
		CartItems cartItems = cartItem.get();
		cartItems.setQuantity(quantity);
		cartItems.setTotalPrice(cartItems.getTotalPrice()*quantity);
		
		return cartItemRepository.save(cartItems);
	}

	
	@Override
	public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		Cart cart = cartRepository.findByCustomerId(user.getId());
		Optional<CartItems> cartItem1 = cartItemRepository.findById(cartItemId);
		if(cartItem1.isEmpty()) {
			throw new  Exception ("cartItem is not present");
		}
		CartItems cartItems = cartItem1.get();
		cart.getItems().remove(cartItems);
	

		return cartRepository.save(cart);
	}

	@Override
	public Long calculateCartTotal(Cart cart) throws Exception {
		Long total =0l;
		
		
		for(CartItems cartItems: cart.getItems()) {
			total = cartItems.getFood().getPrice()*cartItems.getQuantity();
					
		}
		
		return total;
	}

	@Override
	public Cart findCardById(long id) throws Exception {
		
		
		Optional<Cart> cartidOp = cartRepository.findById(id);
		if(cartidOp.isEmpty()) {
			throw new Exception(" cart not found "+id);
		}
		// TODO Auto-generated method stub
		return cartidOp.get();
	}

	@Override
	public Cart findCardByUserId(long userId ) throws Exception {
//		User user = userService.findUserByJwtToken(jwt);
		Cart cart = cartRepository.findByCustomerId(userId);
		cart.setTotal(calculateCartTotal(cart));
	
		return cart;
	}

	@Override
	public Cart clearCart(long userId) throws Exception {
//		User userByJwtToken = userService.findUserByJwtToken(jwt);
	
		Cart cart = findCardById(userId);
		return cartRepository.save(cart);
	}

}
