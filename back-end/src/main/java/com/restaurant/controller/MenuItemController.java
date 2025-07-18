package com.restaurant.controller;

import com.restaurant.entity.MenuItems;
import com.restaurant.repository.MenuItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// TODO ADD comments
@RestController
@RequestMapping("/api")
public class MenuItemController {

    private static final Logger logger = LoggerFactory.getLogger(MenuItemController.class);

    @Autowired
    private MenuItemRepository menuItemRepository;

    // Get all menu items
    @GetMapping("/menuitems")
    public List<MenuItems> getAllMenuItems() {
        List<MenuItems> items = menuItemRepository.findAll();
        return items;
    }

    @PostMapping("/menuitems")
    public ResponseEntity<MenuItems> addMenuItem(@RequestBody MenuItems menuItem) {
        MenuItems newMenuItem = menuItemRepository.save(menuItem);
        return new ResponseEntity<>(newMenuItem, HttpStatus.CREATED);
    }

    // Get menu item by ID
    @GetMapping("/menuitems/{id}")
    public ResponseEntity<MenuItems> getMenuItemById(@PathVariable(value = "id") String id) {
        Optional<MenuItems> menuItem = menuItemRepository.findById(id);
        if (menuItem.isPresent()) {
            return ResponseEntity.ok().body(menuItem.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/menuitems/{id}")
    public ResponseEntity<?> updateMenuItem(@PathVariable(value = "id") String id,
                                            @RequestBody MenuItems menuItem) {
        try {
            Optional<MenuItems> optionalMenuItems = menuItemRepository.findById(id);
            if(optionalMenuItems.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No menu item with provided id: " + id);
            }
            MenuItems menuItems = optionalMenuItems.get();

            menuItems.setName(menuItem.getName());
            menuItems.setAvailable(menuItem.getAvailable());
            menuItems.setCategory(menuItem.getCategory());
            menuItems.setImageurl(menuItem.getImageurl());
            menuItems.setDescription(menuItem.getDescription());
            menuItems.setPrice(menuItem.getPrice());

            MenuItems updatedMenuItem = menuItemRepository.save(menuItems);
            return ResponseEntity.ok(updatedMenuItem);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the user: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/menuitems/{id}")
    public ResponseEntity<?> deleteMenuItem(@PathVariable(value = "id") String id) {
        try {
            Optional<MenuItems> optionalMenuItem = menuItemRepository.findById(id);
            if (optionalMenuItem.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Menu item not found with id: " + id);
            }
            menuItemRepository.deleteById(id);
            return ResponseEntity.ok("Menu item deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the user: " + e.getMessage());
        }
    }



    // Get menu items by category custom call
    @GetMapping("/menuitems/category/{category}")
    public List<MenuItems> getMenuItemsByCategory(@PathVariable(value = "category") String category) {
        logger.info("Fetching menu items with category: {}", category);
        List<MenuItems> items = menuItemRepository.findByCategory(category);
        logger.info("Found {} items in category {}", items.size(), category);
        return items;
    }

    // Test endpoint to verify API connectivity
//    @GetMapping("/test")
//    public ResponseEntity<String> testEndpoint() {
//        logger.info("Test endpoint called");
//        return ResponseEntity.ok("API is working properly!");
//    }
}
