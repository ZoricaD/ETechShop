package com.example.etechshop.service.impl;

import com.example.etechshop.entity.*;
import com.example.etechshop.entity.enums.OrderStatus;
import com.example.etechshop.repository.OrderRepository;
import com.example.etechshop.repository.OrderItemRepository;
import com.example.etechshop.repository.ProductRepository;
import com.example.etechshop.repository.TechUserRepository;
import com.example.etechshop.service.OrderService;
import com.example.etechshop.service.CartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final TechUserRepository techUserRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
                            TechUserRepository techUserRepository, ProductRepository productRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.techUserRepository = techUserRepository;
        this.productRepository = productRepository;
        this.cartService = cartService;
    }

    @Override
    @Transactional
    public Order createOrder(Long userId) {
        TechUser user = techUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartService.getCartByUserId(userId);
        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty, cannot place order");
        }

        double totalPrice = cart.getCartItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        Order order = Order.builder()
                .techUser(user)
                .totalPrice(totalPrice)
                .status(OrderStatus.PENDING)
                .build();

        order = orderRepository.save(order);

        final Order savedOrder = order;

        List<OrderItem> orderItems = cart.getCartItems().stream().map(cartItem -> OrderItem.builder()
                .order(savedOrder)
                .product(cartItem.getProduct())
                .quantity(cartItem.getQuantity())
                .price(cartItem.getProduct().getPrice() * cartItem.getQuantity())
                .build()).collect(Collectors.toList());

        orderItemRepository.saveAll(orderItems);

        savedOrder.setOrderItems(orderItems);

        cartService.clearCart(userId);

        return orderRepository.save(savedOrder);
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findAll().stream()
                .filter(order -> order.getTechUser().getId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional
    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (status == OrderStatus.COMPLETED) {
            for (OrderItem item : existingOrder.getOrderItems()) {
                Product product = item.getProduct();
                int newQuantity = product.getQuantity() - item.getQuantity();

                if (newQuantity < 0) {
                    throw new RuntimeException("Not enough stock for product " + product.getName());
                }

                product.setQuantity(newQuantity);
                productRepository.save(product);
            }
        }

        existingOrder.setStatus(status);

        return orderRepository.save(existingOrder);
    }

    @Override
    @Transactional
    public void deleteOrder(Long orderId) {
        Order order = getOrderById(orderId);
        orderRepository.delete(order);
    }
}
