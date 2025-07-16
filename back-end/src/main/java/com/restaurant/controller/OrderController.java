package com.restaurant.controller;

import com.restaurant.entity.Orders;
import com.restaurant.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/orders")
    public List<Orders> getAllOrders() {
        List<Orders> orders = new ArrayList<>();
        orderRepository.findAll().forEach(orders::add);
        return orders;
    }

}
