package com.techlab.store.controller;

import com.techlab.store.entity.Order;
import com.techlab.store.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders") // Endpoint base
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return this.orderService.createOrder(order);
    }


    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return this.orderService.getOrderById(id);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return this.orderService.getAllOrders();
    }


    @PutMapping("/{id}/status")
    public Order updateStatus(
            @PathVariable Long id,
            @RequestParam Order.orderState newState) {
        return this.orderService.updateOrderStatus(id, newState);
    }
}