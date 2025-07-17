package com.restaurant.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import com.restaurant.entity.Item;
import com.restaurant.repository.ItemRepository;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemController itemController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Item testItem;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
        objectMapper = new ObjectMapper();
        
        // Create a test item
        testItem = new Item();
        testItem.setItemId(1);
        testItem.setOrder("order123");
        testItem.setMenuItem("menu123");
        testItem.setPrice(12.99);
        testItem.setNotes("No pickles");
        testItem.setFirstName("Customer");
    }

    @Test
    @DisplayName("GET /api/items - Should return all items")
    void testGetItems() throws Exception {
        // Arrange
        List<Item> items = new ArrayList<>();
        items.add(testItem);
        
        when(itemRepository.findAll()).thenReturn(items);

        // Act & Assert
        mockMvc.perform(get("/api/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].order", is(testItem.getOrder())));

        verify(itemRepository).findAll();
    }
    
    @Test
    @DisplayName("GET /api/items - Should return empty list when no items exist")
    void testGetItemsEmpty() throws Exception {
        // Arrange
        when(itemRepository.findAll()).thenReturn(new ArrayList<>());

        // Act & Assert
        mockMvc.perform(get("/api/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(itemRepository).findAll();
    }

    @Test
    @DisplayName("GET /api/items/{id} - Should return item by ID")
    void testGetItemById() throws Exception {
        // Arrange
        when(itemRepository.findById("1")).thenReturn(Optional.of(testItem));

        // Act & Assert
        mockMvc.perform(get("/api/items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.order", is(testItem.getOrder())))
                .andExpect(jsonPath("$.price", is(testItem.getPrice())));

        verify(itemRepository).findById("1");
    }

    @Test
    @DisplayName("GET /api/items/{id} - Should return 404 when item not found")
    void testGetItemByIdNotFound() throws Exception {
        // Arrange
        when(itemRepository.findById("999")).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/items/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Item not found")));

        verify(itemRepository).findById("999");
    }
    
    @Test
    @DisplayName("GET /api/items/{id} - Should handle exception")
    void testGetItemByIdException() throws Exception {
        // Arrange
        when(itemRepository.findById(anyString())).thenThrow(new RuntimeException("Database connection error"));

        // Act & Assert
        mockMvc.perform(get("/api/items/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("An error occurred while fetching the user")));

        verify(itemRepository).findById("1");
    }

    @Test
    @DisplayName("PUT /api/items/{id} - Should update item")
    void testUpdateItem() throws Exception {
        // Arrange
        Item updatedItem = testItem;
        updatedItem.setNotes("Extra sauce");
        updatedItem.setPrice(14.99);
        
        when(itemRepository.findById("1")).thenReturn(Optional.of(testItem));
        when(itemRepository.save(any(Item.class))).thenReturn(updatedItem);

        // Act & Assert
        mockMvc.perform(put("/api/items/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedItem)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notes", is("Extra sauce")))
                .andExpect(jsonPath("$.price", is(14.99)));

        verify(itemRepository).findById("1");
        verify(itemRepository).save(any(Item.class));
    }
    
    @Test
    @DisplayName("PUT /api/items/{id} - Should return 404 when item not found")
    void testUpdateItemNotFound() throws Exception {
        // Arrange
        Item updatedItem = testItem;
        updatedItem.setNotes("Extra sauce");
        
        when(itemRepository.findById("999")).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(put("/api/items/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedItem)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Item not found")));

        verify(itemRepository).findById("999");
        verify(itemRepository, never()).save(any(Item.class));
    }
    
    @Test
    @DisplayName("PUT /api/items/{id} - Should handle exception")
    void testUpdateItemException() throws Exception {
        // Arrange
        Item updatedItem = testItem;
        
        when(itemRepository.findById("1")).thenReturn(Optional.of(testItem));
        when(itemRepository.save(any(Item.class))).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        mockMvc.perform(put("/api/items/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedItem)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("An error occurred")));

        verify(itemRepository).findById("1");
        verify(itemRepository).save(any(Item.class));
    }

    @Test
    @DisplayName("DELETE /api/items/{id} - Should delete item")
    void testDeleteItem() throws Exception {
        // Arrange
        when(itemRepository.findById("1")).thenReturn(Optional.of(testItem));
        doNothing().when(itemRepository).deleteById("1");

        // Act & Assert
        mockMvc.perform(delete("/api/items/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Item deleted successfully")));

        verify(itemRepository).findById("1");
        verify(itemRepository).deleteById("1");
    }
    
    @Test
    @DisplayName("DELETE /api/items/{id} - Should return 404 when item not found")
    void testDeleteItemNotFound() throws Exception {
        // Arrange
        when(itemRepository.findById("999")).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(delete("/api/items/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Item not found")));

        verify(itemRepository).findById("999");
        verify(itemRepository, never()).deleteById(anyString());
    }
    
    @Test
    @DisplayName("DELETE /api/items/{id} - Should handle exception")
    void testDeleteItemException() throws Exception {
        // Arrange
        when(itemRepository.findById("1")).thenReturn(Optional.of(testItem));
        doThrow(new RuntimeException("Database error")).when(itemRepository).deleteById("1");

        // Act & Assert
        mockMvc.perform(delete("/api/items/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("An error occurred while deleting")));

        verify(itemRepository).findById("1");
        verify(itemRepository).deleteById("1");
    }

    @Test
    @DisplayName("GET /api/items/order/{orderid} - Should return items by order ID")
    void testGetAllItemsByOrderId() throws Exception {
        // Arrange
        List<Item> orderItems = new ArrayList<>();
        orderItems.add(testItem);
        
        when(itemRepository.findAllByOrder("order123")).thenReturn(orderItems);

        // Act & Assert
        mockMvc.perform(get("/api/items/order/order123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].order", is("order123")));

        verify(itemRepository).findAllByOrder("order123");
    }

    @Test
    @DisplayName("GET /api/items/order/{orderid} - Should return 404 when no items found for order")
    void testGetAllItemsByOrderIdNotFound() throws Exception {
        // Arrange
        when(itemRepository.findAllByOrder("nonexistent")).thenReturn(new ArrayList<>());

        // Act & Assert
        mockMvc.perform(get("/api/items/order/nonexistent"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("No items found for order ID")));

        verify(itemRepository).findAllByOrder("nonexistent");
    }
    
    @Test
    @DisplayName("GET /api/items/order/{orderid} - Should handle repository exception")
    void testGetAllItemsByOrderIdException() throws Exception {
        // Arrange
        when(itemRepository.findAllByOrder(anyString())).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        mockMvc.perform(get("/api/items/order/order123"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("An error occurred while fetching items")));

        verify(itemRepository).findAllByOrder("order123");
    }

    @Test
    @DisplayName("POST /api/items/order/{orderId} - Should add items to order")
    void testUpdateItemsToOrder() throws Exception {
        // Arrange
        List<Item> items = new ArrayList<>();
        items.add(testItem);
        
        when(itemRepository.saveAll(anyIterable())).thenReturn(items);

        // Act & Assert
        mockMvc.perform(post("/api/items/order/order123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(items)))
                .andExpect(status().isCreated());

        verify(itemRepository).saveAll(anyIterable());
    }
    
    @Test
    @DisplayName("POST /api/items/order/{orderId} - Should handle exception")
    void testUpdateItemsToOrderException() throws Exception {
        // Arrange
        List<Item> items = new ArrayList<>();
        items.add(testItem);
        
        when(itemRepository.saveAll(anyIterable())).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        mockMvc.perform(post("/api/items/order/order123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(items)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("An error occurred while adding items")));

        verify(itemRepository).saveAll(anyIterable());
    }

    @Test
    @DisplayName("DELETE /api/items/order/{orderid} - Should delete all items for order")
    void testDeleteItemsFromOrder() throws Exception {
        // Arrange
        List<Item> items = new ArrayList<>();
        items.add(testItem);
        
        when(itemRepository.findAllByOrder("order123")).thenReturn(items);
        doNothing().when(itemRepository).deleteAll(anyIterable());

        // Act & Assert
        mockMvc.perform(delete("/api/items/order/order123"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("All items for order ID order123 have been deleted")));

        verify(itemRepository).findAllByOrder("order123");
        verify(itemRepository).deleteAll(anyIterable());
    }
    
    @Test
    @DisplayName("DELETE /api/items/order/{orderid} - Should return 404 when no items found")
    void testDeleteItemsFromOrderNotFound() throws Exception {
        // Arrange
        when(itemRepository.findAllByOrder("nonexistent")).thenReturn(new ArrayList<>());

        // Act & Assert
        mockMvc.perform(delete("/api/items/order/nonexistent"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("No items found for order ID")));

        verify(itemRepository).findAllByOrder("nonexistent");
        verify(itemRepository, never()).deleteAll(anyIterable());
    }
    
    @Test
    @DisplayName("DELETE /api/items/order/{orderid} - Should handle exception")
    void testDeleteItemsFromOrderException() throws Exception {
        // Arrange
        List<Item> items = new ArrayList<>();
        items.add(testItem);
        
        when(itemRepository.findAllByOrder("order123")).thenReturn(items);
        doThrow(new RuntimeException("Database error")).when(itemRepository).deleteAll(anyIterable());

        // Act & Assert
        mockMvc.perform(delete("/api/items/order/order123"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("An error occurred while deleting items")));

        verify(itemRepository).findAllByOrder("order123");
        verify(itemRepository).deleteAll(anyIterable());
    }
}
