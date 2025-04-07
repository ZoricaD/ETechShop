package com.example.etechshop.service.impl;

import com.example.etechshop.entity.Order;
import com.example.etechshop.entity.OrderItem;
import com.example.etechshop.entity.Product;
import com.example.etechshop.repository.OrderItemRepository;
import com.example.etechshop.repository.OrderRepository;
import com.example.etechshop.repository.ProductRepository;
import com.example.etechshop.service.OrderItemService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository,
                                OrderRepository orderRepository,
                                ProductRepository productRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public OrderItem getOrderItemById(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found"));
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    @Override
    @Transactional
    public OrderItem createOrderItem(Long orderId, Long productId, int quantity, double price) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }


        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(quantity)
                .price(price)
                .build();


        return orderItemRepository.save(orderItem);
    }

    @Override
    public List<OrderItem> getOrderItemsByOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return order.getOrderItems();
    }



    @Override
    @Transactional
    public OrderItem updateOrderItem(Long id, int quantity, Double price) {
        OrderItem orderItem = getOrderItemById(id);

        if (quantity > 0) {
            orderItem.setQuantity(quantity);
        }

        if (price != null && price > 0) {
            orderItem.setPrice(price);
        }

        return orderItemRepository.save(orderItem);
    }

    @Override
    @Transactional
    public void deleteOrderItem(Long id) {
        OrderItem orderItem = getOrderItemById(id);
        orderItemRepository.delete(orderItem);
    }
}
