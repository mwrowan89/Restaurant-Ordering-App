package com.restaurant.controller;

import com.restaurant.entity.Film;
import com.restaurant.entity.Orders;
import com.restaurant.entity.User;
import com.restaurant.repository.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    // View all orders in DB
    @GetMapping("/orders")
    public ResponseEntity<?> getAllOrders() {
        try {
            List<Orders> orders = new ArrayList<>();
            orderRepository.findAll().forEach(orders::add);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while retrieving orders.");
        }
    }

    // Create an order
    @PostMapping("/orders")
    public ResponseEntity<?> createOrder(@RequestBody Orders order) {
        try {
            Orders savedOrder = orderRepository.save(order);

            // Build a response map containing just the orderId
            Map<String, Object> response = new HashMap<>();
            response.put("orderId", savedOrder.getId());

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create order: " + e.getMessage());
        }
    }

    // Get order by the order Id
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> getOrderByOrderId(@PathVariable(value = "id") String orderId) {
        try {
            Optional<Orders> user = orderRepository.findById(orderId);
            if (user.isPresent()) {
                return ResponseEntity.ok(user.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Order not found with id: " + orderId);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the order: " + e.getMessage());
        }
    }

    // Update order information
    @PutMapping("/orders/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable(value = "id") String orderId,
                                         @RequestBody Orders orderDetails) {
        try {
            Optional<Orders> optionalOrder = orderRepository.findById(orderId);
            if (!optionalOrder.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Order not found with id: " + orderId);
            }

            Orders order = optionalOrder.get();

            order.setUserid(orderDetails.getUserid());
            order.setOrderTime(orderDetails.getOrderTime());
            order.setPickupTime(orderDetails.getPickupTime());
            order.setArea(orderDetails.getArea());
            order.setLocation(orderDetails.getLocation());
            order.setTax(orderDetails.getTax());
            order.setTip(orderDetails.getTip());
            order.setPan(orderDetails.getPan());
            order.setExpiryMonth(orderDetails.getExpiryMonth());
            order.setExpiryYear(orderDetails.getExpiryYear());
            order.setStatus(orderDetails.getStatus());

            Orders updatedOrder = orderRepository.save(order);
            return ResponseEntity.ok(updatedOrder);

        } catch (Exception e) {
        // TODO add exception classes
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the user: " + e.getMessage());
        }
    }

    // Delete an order entry
    // TODO add auth and uncomment
    // @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/orders/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable(value = "id") String orderId) {
        try {
            Optional<Orders> optionalOrder = orderRepository.findById(orderId);
            if (!optionalOrder.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Order not found with id: " + orderId);
            }
            orderRepository.deleteById(orderId);
            return ResponseEntity.ok("Order deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the user: " + e.getMessage());
        }
    }

    // Get all orders for a specific user
    @GetMapping("/orders/user/{userid}")
    public ResponseEntity<?> getOrdersByUserId(@PathVariable("userid") String userid) {
        try {
            List<Orders> orders = orderRepository.findByUserid(userid);
            if (orders.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No orders found for userId: " + userid);
            }
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching orders: " + e.getMessage());
        }
    }

}
