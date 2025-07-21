package com.restaurant.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.restaurant.entity.Orders;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE) // Use the real database
public class OrderRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("Should find orders by user ID")
    void testFindByUserid() {
        // Arrange
        Orders order1 = new Orders();
        order1.setUserid("user123");
        order1.setOrderTime(LocalDateTime.now());
        order1.setStatus("PENDING");
        entityManager.persist(order1);
        
        Orders order2 = new Orders();
        order2.setUserid("user123");
        order2.setOrderTime(LocalDateTime.now().plusHours(1));
        order2.setStatus("COMPLETED");
        entityManager.persist(order2);
        
        Orders order3 = new Orders();
        order3.setUserid("user456");
        order3.setOrderTime(LocalDateTime.now());
        order3.setStatus("PENDING");
        entityManager.persist(order3);
        
        entityManager.flush();
        
        // Act
        List<Orders> foundOrders = orderRepository.findByUserid("user123");
        
        // Assert
        assertEquals(2, foundOrders.size(), "Should find 2 orders for user123");
        assertThat(foundOrders).extracting(Orders::getUserid).containsOnly("user123");
    }

    @Test
    @DisplayName("Should save and find order by ID")
    void testSaveAndFindById() {
        // Arrange
        Orders order = new Orders();
        order.setUserid("user123");
        order.setOrderTime(LocalDateTime.now());
        order.setPickupTime(LocalDateTime.now().plusHours(1));
        order.setArea("Main");
        order.setLocation("Downtown");
        order.setTax(5.99);
        order.setTip(3.50);
        order.setPan("1234");
        order.setExpiryMonth(12);
        order.setExpiryYear(2025);
        order.setStatus("PENDING");
        
        // Act
        Orders savedOrder = entityManager.persistAndFlush(order);
        Optional<Orders> foundOrder = orderRepository.findById(savedOrder.getId().toString());
        
        // Assert
        assertThat(foundOrder).isPresent();
        assertThat(foundOrder.get()).usingRecursiveComparison().isEqualTo(order);
    }
    
    @Test
    @DisplayName("Should update an order")
    void testUpdateOrder() {
        // Arrange
        Orders order = new Orders();
        order.setUserid("user123");
        order.setStatus("PENDING");
        Orders savedOrder = entityManager.persistAndFlush(order);
        
        // Act
        Orders orderToUpdate = orderRepository.findById(savedOrder.getId().toString()).get();
        orderToUpdate.setStatus("COMPLETED");
        orderRepository.save(orderToUpdate);
        
        // Assert
        Orders updatedOrder = orderRepository.findById(savedOrder.getId().toString()).get();
        assertEquals("COMPLETED", updatedOrder.getStatus(), "Order status should be updated");
    }
    
    @Test
    @DisplayName("Should delete an order")
    void testDeleteOrder() {
        // Arrange
        Orders order = new Orders();
        order.setUserid("user123");
        order.setStatus("PENDING");
        Orders savedOrder = entityManager.persistAndFlush(order);
        
        // Act
        orderRepository.deleteById(savedOrder.getId().toString());
        Optional<Orders> foundOrder = orderRepository.findById(savedOrder.getId().toString());
        
        // Assert
        assertThat(foundOrder).isEmpty();
    }
}
