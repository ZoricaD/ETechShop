package com.example.etechshop.controller;

import com.example.etechshop.entity.CartItem;
import com.example.etechshop.service.CartItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cart-items")
@CrossOrigin(origins = "http://localhost:3000")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping("/{userId}/{productId}")
    public ResponseEntity<CartItem> addCartItem(@PathVariable Long userId,
                                                @PathVariable Long productId,
                                                @RequestParam int quantity) {
        CartItem cartItem = cartItemService.addCartItem(userId, productId, quantity);
        return ResponseEntity.ok(cartItem);
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItem(@PathVariable Long cartItemId,
                                                   @RequestParam int quantity) {
        CartItem updatedCartItem = cartItemService.updateCartItem(cartItemId, quantity);
        return ResponseEntity.ok(updatedCartItem);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long cartItemId) {
        cartItemService.removeCartItem(cartItemId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cart/{cartId}")
    public ResponseEntity<List<CartItem>> getCartItems(@PathVariable Long cartId) {
        List<CartItem> cartItems = cartItemService.getCartItemsByCart(cartId);
        return ResponseEntity.ok(cartItems);
    }
}
