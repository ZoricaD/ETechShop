package com.example.etechshop.service;

import com.example.etechshop.entity.Order;
import com.example.etechshop.entity.enums.OrderStatus;

import java.util.List;

public interface OrderService {
    Order createOrder(Long userId);
    Order getOrderById(Long orderId);
    List<Order> getOrdersByUserId(Long userId);
    List<Order> getAllOrders();
    Order updateOrderStatus(Long orderId, OrderStatus status);
    void deleteOrder(Long orderId);
}
