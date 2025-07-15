package com.restaurant.controller;

import com.restaurant.entity.MenuItems;
import com.restaurant.repository.MenuItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MenuItemController {

    private static final Logger logger = LoggerFactory.getLogger(MenuItemController.class);

    @Autowired
    private MenuItemRepository menuItemRepository;

    // Get all menu items
    @GetMapping("/menuitems")
    public List<MenuItems> getAllMenuItems() {
        logger.info("Fetching all menu items");
        List<MenuItems> items = menuItemRepository.findAll();
        logger.info("Found {} menu items", items.size());
        return items;
    }

    // Get menu item by ID
    @GetMapping("/menuitems/{id}")
    public ResponseEntity<MenuItems> getMenuItemById(@PathVariable(value = "id") String menuItemId) {
        logger.info("Fetching menu item with ID: {}", menuItemId);
        Optional<MenuItems> menuItem = menuItemRepository.findById(menuItemId);
        if (menuItem.isPresent()) {
            logger.info("Found menu item: {}", menuItem.get().getName());
            return ResponseEntity.ok().body(menuItem.get());
        } else {
            logger.warn("Menu item not found with ID: {}", menuItemId);
            return ResponseEntity.notFound().build();
        }
    }

    // Redirect singular form to plural
    @GetMapping("/menuitem")
    public List<MenuItems> redirectSingularToPlural() {
        logger.info("Redirecting /menuitem to /menuitems");
        return getAllMenuItems();
    }

    // Get menu items by category
    @GetMapping("/menuitems/category/{category}")
    public List<MenuItems> getMenuItemsByCategory(@PathVariable(value = "category") String category) {
        logger.info("Fetching menu items with category: {}", category);
        List<MenuItems> items = menuItemRepository.findByCategory(category);
        logger.info("Found {} items in category {}", items.size(), category);
        return items;
    }

    // Test endpoint to verify API connectivity
    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        logger.info("Test endpoint called");
        return ResponseEntity.ok("API is working properly!");
    }
}
