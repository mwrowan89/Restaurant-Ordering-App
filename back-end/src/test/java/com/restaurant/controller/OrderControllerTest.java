package com.restaurant.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.restaurant.entity.Orders;
import com.restaurant.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderController orderController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Orders testOrder;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // For LocalDateTime serialization
        
        // Create a test order
        testOrder = new Orders();
        testOrder.setId(1L);
        testOrder.setUserid("user123");
        testOrder.setOrderTime(LocalDateTime.now());
        testOrder.setPickupTime(LocalDateTime.now().plusHours(1));
        testOrder.setArea("Main");
        testOrder.setLocation("Downtown");
        testOrder.setTax(5.99);
        testOrder.setTip(3.50);
        testOrder.setPan("1234");
        testOrder.setExpiryMonth(12);
        testOrder.setExpiryYear(2025);
        testOrder.setStatus("PENDING");
    }

    @Test
    @DisplayName("GET /api/orders - Should return all orders")
    void testGetAllOrders() throws Exception {
        // Arrange
        List<Orders> orders = new ArrayList<>();
        orders.add(testOrder);
        
        when(orderRepository.findAll()).thenReturn(orders);

        // Act & Assert
        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(testOrder.getId().intValue())))
                .andExpect(jsonPath("$[0].userid", is(testOrder.getUserid())));

        verify(orderRepository).findAll();
    }

    @Test
    @DisplayName("POST /api/orders - Should create a new order")
    void testCreateOrder() throws Exception {
        // Arrange
        when(orderRepository.save(any(Orders.class))).thenReturn(testOrder);

        // Act & Assert
        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testOrder)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderId", is(testOrder.getId().intValue())));

        verify(orderRepository).save(any(Orders.class));
    }

    @Test
    @DisplayName("GET /api/orders/{id} - Should return order by ID")
    void testGetOrderByOrderId() throws Exception {
        // Arrange
        when(orderRepository.findById("1")).thenReturn(Optional.of(testOrder));

        // Act & Assert
        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(testOrder.getId().intValue())))
                .andExpect(jsonPath("$.userid", is(testOrder.getUserid())));

        verify(orderRepository).findById("1");
    }

    @Test
    @DisplayName("GET /api/orders/{id} - Should return 404 when order not found")
    void testGetOrderByOrderIdNotFound() throws Exception {
        // Arrange
        when(orderRepository.findById("999")).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/orders/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Order not found")));

        verify(orderRepository).findById("999");
    }

    @Test
    @DisplayName("PUT /api/orders/{id} - Should update order")
    void testUpdateOrder() throws Exception {
        // Arrange
        Orders updatedOrder = testOrder;
        updatedOrder.setStatus("COMPLETED");
        
        when(orderRepository.findById("1")).thenReturn(Optional.of(testOrder));
        when(orderRepository.save(any(Orders.class))).thenReturn(updatedOrder);

        // Act & Assert
        mockMvc.perform(put("/api/orders/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedOrder)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("COMPLETED")));

        verify(orderRepository).findById("1");
        verify(orderRepository).save(any(Orders.class));
    }

    @Test
    @DisplayName("DELETE /api/orders/{id} - Should delete order")
    void testDeleteOrder() throws Exception {
        // Arrange
        when(orderRepository.findById("1")).thenReturn(Optional.of(testOrder));
        doNothing().when(orderRepository).deleteById("1");

        // Act & Assert
        mockMvc.perform(delete("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Order deleted successfully")));

        verify(orderRepository).findById("1");
        verify(orderRepository).deleteById("1");
    }

    @Test
    @DisplayName("GET /api/orders/user/{userid} - Should return orders for user")
    void testGetOrdersByUserId() throws Exception {
        // Arrange
        List<Orders> userOrders = new ArrayList<>();
        userOrders.add(testOrder);
        
        when(orderRepository.findByUserid("user123")).thenReturn(userOrders);

        // Act & Assert
        mockMvc.perform(get("/api/orders/user/user123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userid", is("user123")));

        verify(orderRepository).findByUserid("user123");
    }

    @Test
    @DisplayName("GET /api/orders/user/{userid} - Should return 404 when no orders found for user")
    void testGetOrdersByUserIdNotFound() throws Exception {
        // Arrange
        when(orderRepository.findByUserid("nonexistent")).thenReturn(new ArrayList<>());

        // Act & Assert
        mockMvc.perform(get("/api/orders/user/nonexistent"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("No orders found for userId")));

        verify(orderRepository).findByUserid("nonexistent");
    }
}
