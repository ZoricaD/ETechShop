package com.example.etechshop.service;

import com.example.etechshop.entity.Cart;
import com.example.etechshop.entity.CartItem;

import java.util.List;

public interface CartService {
    Cart createCart(Long userId);
    Cart getCartByUserId(Long userId);
    List<CartItem> getCartItems(Long userId);
    Cart addProductToCart(Long userId, Long productId, int quantity);
    Cart updateCart(Long userId, Long cartItemId, int quantity);
    Cart removeCartItem(Long userId, Long cartItemId);
    void clearCart(Long userId);
}
