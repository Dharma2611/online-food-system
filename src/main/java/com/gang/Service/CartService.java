package com.gang.Service;

import com.gang.Entity.Cart;
import com.gang.Entity.CartItems;
import com.gang.Request.AddToCartItemRequest;

public interface CartService {
	public CartItems addToCart(AddToCartItemRequest req , String jwt) throws Exception;
	public CartItems updateCartItemQuantity(Long cartItemId , int quantity ) throws Exception;
	public Cart removeItemFromCart(Long cartItemId , String jwt) throws Exception;
	public Long calculateCartTotal(Cart cart) throws Exception;
	public Cart findCardById(long id)throws Exception;
	public  Cart findCardByUserId(long userId) throws Exception;
	public Cart clearCart(long userId)throws Exception;
	

}
