package com.example.etechshop.service;

import com.example.etechshop.entity.CartItem;

import java.util.List;

public interface CartItemService {
    CartItem addCartItem(Long userId, Long productId, int quantity);
    CartItem updateCartItem(Long cartItemId, int quantity);
    void removeCartItem(Long cartItemId);
    CartItem getCartItemById(Long cartItemId);
    List<CartItem> getCartItemsByCart(Long cartId);
}
