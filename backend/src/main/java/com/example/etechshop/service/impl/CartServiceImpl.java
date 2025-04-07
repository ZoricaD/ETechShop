package com.example.etechshop.service.impl;

import com.example.etechshop.entity.Cart;
import com.example.etechshop.entity.CartItem;
import com.example.etechshop.entity.Product;
import com.example.etechshop.entity.TechUser;
import com.example.etechshop.repository.CartItemRepository;
import com.example.etechshop.repository.CartRepository;
import com.example.etechshop.repository.ProductRepository;
import com.example.etechshop.repository.TechUserRepository;
import com.example.etechshop.service.CartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final TechUserRepository techUserRepository;

    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository,
                           ProductRepository productRepository, TechUserRepository techUserRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.techUserRepository = techUserRepository;
    }

    @Override
    public Cart createCart(Long userId) {
        TechUser user = techUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Проверете дали кошничката веќе постои за корисникот
        Optional<Cart> existingCart = cartRepository.findByTechUserId(userId);
        if (existingCart.isPresent()) {
            return existingCart.get(); // Врати ја постоечката кошничка
        } else {
            // Ако кошничката не постои, креирај нова
            Cart cart = Cart.builder()
                    .techUser(user)
                    .build();
            return cartRepository.save(cart);
        }
    }


    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByTechUserId(userId)
                .orElseGet(() -> createCart(userId));
    }


    @Override
    @Transactional
    public List<CartItem> getCartItems(Long userId) {
        Cart cart = getCartByUserId(userId);
        return cart.getCartItems();
    }

    @Override
    @Transactional
    public Cart addProductToCart(Long userId, Long productId, int quantity) {
        Cart cart = getCartByUserId(userId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<CartItem> existingCartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemRepository.save(cartItem);
        } else {
            CartItem newCartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(quantity)
                    .build();
            cart.getCartItems().add(newCartItem);
            cartItemRepository.save(newCartItem);
        }

        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public Cart updateCart(Long userId, Long cartItemId, int quantity) {
        Cart cart = getCartByUserId(userId);

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!cart.getCartItems().contains(cartItem)) {
            throw new RuntimeException("Cart item does not belong to this cart");
        }

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);

        return cart;
    }


    @Override
    @Transactional
    public Cart removeCartItem(Long userId, Long cartItemId) {
        Cart cart = getCartByUserId(userId);

        cart.getCartItems().removeIf(cartItem -> {
            if (cartItem.getId().equals(cartItemId)) {
                cartItemRepository.delete(cartItem);
                return true;
            }
            return false;
        });

        return cartRepository.save(cart);
    }


    @Override
    @Transactional
    public void clearCart(Long userId) {
        Cart cart = getCartByUserId(userId);
        cartItemRepository.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }
}
