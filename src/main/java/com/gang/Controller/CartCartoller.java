package com.gang.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gang.Entity.Cart;
import com.gang.Entity.CartItems;
import com.gang.Entity.User;
import com.gang.Request.AddToCartItemRequest;
import com.gang.Request.UpdateCartRequest;
import com.gang.Service.CartService;
import com.gang.Service.UserService;

@RestController
@RequestMapping("/api")
public class CartCartoller {
	@Autowired
	private CartService cartService;
	@Autowired
	private UserService userService;
	
	@PutMapping("/cart/add")
	public ResponseEntity<CartItems> addToCart(@RequestBody AddToCartItemRequest request,  @RequestHeader("Authorization") String jwt) throws Exception{
		CartItems cartItem = cartService.addToCart(request, jwt);
		return  new ResponseEntity<>(cartItem,HttpStatus.OK);
		
	}
	
	@PutMapping("/cart-item/update")
	public ResponseEntity<CartItems> updateCartItemQuantity( @RequestBody UpdateCartRequest request,     @RequestHeader("Authorization")  String jwt) throws Exception{
		
		CartItems updateCartItemQuantity = cartService.updateCartItemQuantity(request.getCartItemId(), request.getQuantity());
		System.out.println("cartItemIdJi "+request.getCartItemId()+" "+ "quantity "+ request.getQuantity());
		return  new ResponseEntity<>(updateCartItemQuantity,HttpStatus.OK);
		
	}
	@DeleteMapping("/cart-item/{id}/remove")
	public ResponseEntity<Cart> removeItemFromCart(@PathVariable Long id,  @RequestHeader("Authorization") String jwt) throws Exception{
		
		
		Cart removeItemFromCart = cartService.removeItemFromCart(id, jwt);
		return new ResponseEntity<>(removeItemFromCart,HttpStatus.OK);
		
	}
	public ResponseEntity<CartItems> calculateCartTotal(AddToCartItemRequest request,String jwt){
		return null;
		
	}
	
	public ResponseEntity<Cart> getCardById(Long id){
		return null;
		
	}
	
	@GetMapping("/cart")
	public ResponseEntity<Cart> getCardUserId(@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserByJwtToken(jwt);
		Cart cardByUserId = cartService.findCardByUserId(user.getId());
		return new ResponseEntity<>(cardByUserId,HttpStatus.OK);
		
		
	}
	@PutMapping("/cart/clear")
	public ResponseEntity<Cart> clearCart( @RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserByJwtToken(jwt);

		
	Cart clearCart = cartService.clearCart(user.getId());
	
		
	return new ResponseEntity<>(clearCart,HttpStatus.OK);
		
	}
}
