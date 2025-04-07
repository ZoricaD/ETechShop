package com.example.etechshop.service;

import com.example.etechshop.entity.OrderItem;

import java.util.List;

public interface OrderItemService {
    OrderItem getOrderItemById(Long id);
    List<OrderItem> getAllOrderItems();
    OrderItem createOrderItem(Long orderId, Long productId, int quantity, double price);
    OrderItem updateOrderItem(Long id, int quantity, Double price);
    List<OrderItem> getOrderItemsByOrder(Long orderId);
    void deleteOrderItem(Long id);
}
