package com.example.etechshop.controller;

import com.example.etechshop.entity.OrderItem;
import com.example.etechshop.service.OrderItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItem>> getOrderItemsByOrder(@PathVariable Long orderId) {
        List<OrderItem> orderItems = orderItemService.getOrderItemsByOrder(orderId);
        return ResponseEntity.ok(orderItems);
    }

    @PostMapping("/{orderId}/{productId}")
    public ResponseEntity<OrderItem> createOrderItem(@PathVariable Long orderId,
                                                     @PathVariable Long productId,
                                                     @RequestParam int quantity,
                                                     @RequestParam double price) {
        OrderItem orderItem = orderItemService.createOrderItem(orderId, productId, quantity, price);
        return ResponseEntity.ok(orderItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItem> updateOrderItem(@PathVariable Long id,
                                                     @RequestParam int quantity,
                                                     @RequestParam(required = false) Double price) {
        OrderItem updatedOrderItem = orderItemService.updateOrderItem(id, quantity, price);
        return ResponseEntity.ok(updatedOrderItem);
    }
}
