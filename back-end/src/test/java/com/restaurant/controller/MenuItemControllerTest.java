package com.restaurant.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
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
import com.restaurant.entity.MenuItems;
import com.restaurant.repository.MenuItemRepository;

@ExtendWith(MockitoExtension.class)
public class MenuItemControllerTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @InjectMocks
    private MenuItemController menuItemController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private MenuItems testMenuItem;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(menuItemController).build();
        objectMapper = new ObjectMapper();
        
        // Create a test menu item
        testMenuItem = new MenuItems();
        testMenuItem.setId(1L);
        testMenuItem.setName("Test Burger");
        testMenuItem.setDescription("A delicious test burger");
        testMenuItem.setCategory("burger");
        testMenuItem.setPrice(9.99);
        testMenuItem.setImageurl("http://example.com/burger.jpg");
        testMenuItem.setAvailable(true);
    }

    @Test
    @DisplayName("GET /api/menuitems - Should return all menu items")
    void testGetAllMenuItems() throws Exception {
        // Arrange
        List<MenuItems> menuItems = new ArrayList<>();
        menuItems.add(testMenuItem);
        
        when(menuItemRepository.findAll()).thenReturn(menuItems);

        // Act & Assert
        mockMvc.perform(get("/api/menuitems"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(testMenuItem.getName())));

        verify(menuItemRepository).findAll();
    }
    
    @Test
    @DisplayName("GET /api/menuitems - Should return empty list when no items exist")
    void testGetAllMenuItemsEmpty() throws Exception {
        // Arrange
        List<MenuItems> menuItems = new ArrayList<>();
        when(menuItemRepository.findAll()).thenReturn(menuItems);

        // Act & Assert
        mockMvc.perform(get("/api/menuitems"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(menuItemRepository).findAll();
    }

    @Test
    @DisplayName("POST /api/menuitems - Should create a new menu item")
    void testAddMenuItem() throws Exception {
        // Arrange
        when(menuItemRepository.save(any(MenuItems.class))).thenReturn(testMenuItem);

        // Act & Assert
        mockMvc.perform(post("/api/menuitems")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testMenuItem)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(testMenuItem.getName())));

        verify(menuItemRepository).save(any(MenuItems.class));
    }
    
    @Test
    @DisplayName("POST /api/menuitems - Should handle invalid JSON")
    void testAddMenuItemInvalidJson() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/menuitems")
                .contentType(MediaType.APPLICATION_JSON)
                .content("Invalid JSON"))
                .andExpect(status().isBadRequest());
                
        verify(menuItemRepository, never()).save(any(MenuItems.class));
    }

    @Test
    @DisplayName("GET /api/menuitems/{id} - Should return menu item by ID")
    void testGetMenuItemById() throws Exception {
        // Arrange
        when(menuItemRepository.findById("1")).thenReturn(Optional.of(testMenuItem));

        // Act & Assert
        mockMvc.perform(get("/api/menuitems/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(testMenuItem.getName())))
                .andExpect(jsonPath("$.price", is(testMenuItem.getPrice())));

        verify(menuItemRepository).findById("1");
    }

    @Test
    @DisplayName("GET /api/menuitems/{id} - Should return 404 when menu item not found")
    void testGetMenuItemByIdNotFound() throws Exception {
        // Arrange
        when(menuItemRepository.findById("999")).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/menuitems/999"))
                .andExpect(status().isNotFound());

        verify(menuItemRepository).findById("999");
    }

    @Test
    @DisplayName("PUT /api/menuitems/{id} - Should update menu item")
    void testUpdateMenuItem() throws Exception {
        // Arrange
        MenuItems updatedMenuItem = testMenuItem;
        updatedMenuItem.setName("Updated Burger");
        updatedMenuItem.setPrice(11.99);
        
        when(menuItemRepository.findById("1")).thenReturn(Optional.of(testMenuItem));
        when(menuItemRepository.save(any(MenuItems.class))).thenReturn(updatedMenuItem);

        // Act & Assert
        mockMvc.perform(put("/api/menuitems/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedMenuItem)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Burger")))
                .andExpect(jsonPath("$.price", is(11.99)));

        verify(menuItemRepository).findById("1");
        verify(menuItemRepository).save(any(MenuItems.class));
    }
    
    @Test
    @DisplayName("PUT /api/menuitems/{id} - Should return 404 when menu item not found")
    void testUpdateMenuItemNotFound() throws Exception {
        // Arrange
        MenuItems updatedMenuItem = testMenuItem;
        updatedMenuItem.setName("Updated Burger");
        
        when(menuItemRepository.findById("999")).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(put("/api/menuitems/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedMenuItem)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("No menu item with provided id")));

        verify(menuItemRepository).findById("999");
        verify(menuItemRepository, never()).save(any(MenuItems.class));
    }
    
    @Test
    @DisplayName("PUT /api/menuitems/{id} - Should handle repository find exception")
    void testUpdateMenuItemFindException() throws Exception {
        // Arrange
        MenuItems updatedMenuItem = testMenuItem;
        
        when(menuItemRepository.findById(anyString())).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        mockMvc.perform(put("/api/menuitems/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedMenuItem)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("An error occurred while updating the user")));

        verify(menuItemRepository).findById("1");
        verify(menuItemRepository, never()).save(any(MenuItems.class));
    }
    
    @Test
    @DisplayName("PUT /api/menuitems/{id} - Should handle repository save exception")
    void testUpdateMenuItemSaveException() throws Exception {
        // Arrange
        MenuItems updatedMenuItem = testMenuItem;
        
        when(menuItemRepository.findById("1")).thenReturn(Optional.of(testMenuItem));
        when(menuItemRepository.save(any(MenuItems.class))).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        mockMvc.perform(put("/api/menuitems/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedMenuItem)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("An error occurred while updating the user")));

        verify(menuItemRepository).findById("1");
        verify(menuItemRepository).save(any(MenuItems.class));
    }
    
    @Test
    @DisplayName("PUT /api/menuitems/{id} - Should handle invalid JSON")
    void testUpdateMenuItemInvalidJson() throws Exception {
        // Act & Assert
        mockMvc.perform(put("/api/menuitems/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("Invalid JSON"))
                .andExpect(status().isBadRequest());
                
        verify(menuItemRepository, never()).findById(anyString());
        verify(menuItemRepository, never()).save(any(MenuItems.class));
    }

    @Test
    @DisplayName("DELETE /api/menuitems/{id} - Should delete menu item")
    void testDeleteMenuItem() throws Exception {
        // Arrange
        when(menuItemRepository.findById("1")).thenReturn(Optional.of(testMenuItem));
        doNothing().when(menuItemRepository).deleteById("1");

        // Act & Assert
        mockMvc.perform(delete("/api/menuitems/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Menu item deleted successfully")));

        verify(menuItemRepository).findById("1");
        verify(menuItemRepository).deleteById("1");
    }
    
    @Test
    @DisplayName("DELETE /api/menuitems/{id} - Should return 404 when menu item not found")
    void testDeleteMenuItemNotFound() throws Exception {
        // Arrange
        when(menuItemRepository.findById("999")).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(delete("/api/menuitems/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Menu item not found with id")));

        verify(menuItemRepository).findById("999");
        verify(menuItemRepository, never()).deleteById(anyString());
    }
    
    @Test
    @DisplayName("DELETE /api/menuitems/{id} - Should handle repository find exception")
    void testDeleteMenuItemFindException() throws Exception {
        // Arrange
        when(menuItemRepository.findById(anyString())).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        mockMvc.perform(delete("/api/menuitems/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("An error occurred while deleting the user")));

        verify(menuItemRepository).findById("1");
        verify(menuItemRepository, never()).deleteById(anyString());
    }
    
    @Test
    @DisplayName("DELETE /api/menuitems/{id} - Should handle repository delete exception")
    void testDeleteMenuItemDeleteException() throws Exception {
        // Arrange
        when(menuItemRepository.findById("1")).thenReturn(Optional.of(testMenuItem));
        doThrow(new RuntimeException("Database error")).when(menuItemRepository).deleteById("1");

        // Act & Assert
        mockMvc.perform(delete("/api/menuitems/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("An error occurred while deleting the user")));

        verify(menuItemRepository).findById("1");
        verify(menuItemRepository).deleteById("1");
    }

    @Test
    @DisplayName("GET /api/menuitems/category/{category} - Should return menu items by category")
    void testGetMenuItemsByCategory() throws Exception {
        // Arrange
        List<MenuItems> categoryItems = new ArrayList<>();
        categoryItems.add(testMenuItem);
        
        when(menuItemRepository.findByCategory("burger")).thenReturn(categoryItems);

        // Act & Assert
        mockMvc.perform(get("/api/menuitems/category/burger"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].category", is("burger")));

        verify(menuItemRepository).findByCategory("burger");
    }
    
    @Test
    @DisplayName("GET /api/menuitems/category/{category} - Should return empty list when no items exist for category")
    void testGetMenuItemsByCategoryEmpty() throws Exception {
        // Arrange
        List<MenuItems> emptyList = new ArrayList<>();
        when(menuItemRepository.findByCategory("nonexistent")).thenReturn(emptyList);

        // Act & Assert
        mockMvc.perform(get("/api/menuitems/category/nonexistent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(menuItemRepository).findByCategory("nonexistent");
    }
}
