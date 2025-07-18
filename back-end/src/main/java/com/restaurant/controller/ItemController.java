package com.restaurant.controller;

import com.restaurant.entity.Film;
import com.restaurant.entity.Item;
import com.restaurant.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    // Retrieve all items
    @GetMapping("/items")
    public ResponseEntity<?> getItems() {
        try {
            List<Item> items = new ArrayList<>();
            itemRepository.findAll().forEach(items::add);
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while retrieving items.");
        }
    }

    // Get an item by its id
    @GetMapping("/items/{id}")
    public ResponseEntity<?> getItemById(@PathVariable(value = "id") String id) {
        try {
            Optional<Item> item = itemRepository.findById(id);
            if (item.isPresent()) {
                return ResponseEntity.ok(item.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Item not found with id: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the user: " + e.getMessage());
        }
    }

    // Update an item by its id
    @PutMapping("/items/{id}")
    public ResponseEntity<?> updateItem(
            @PathVariable(value = "id") String id,
            @RequestBody Item itemDetails) {
        try {
            Optional<Item> optionalItem = itemRepository.findById(id);
            if (!optionalItem.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Item not found with id: " + id);
            }

            Item item = optionalItem.get();

            item.setOrder(itemDetails.getOrder());
            item.setMenuItem(itemDetails.getMenuItem());
            item.setPrice(itemDetails.getPrice());
            item.setNotes(itemDetails.getNotes());
            item.setFirstName(itemDetails.getFirstName());

            Item updatedItem = itemRepository.save(item);
            return ResponseEntity.ok(updatedItem);

        } catch (Exception e) {
            // TODO add exception classes
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the item: " + e.getMessage());
        }
    }

    // Deletes an item from the list
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/items/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable(value = "id") String id) {
        try {
            Optional<Item> optionalItem = itemRepository.findById(id);
            if (!optionalItem.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Item not found with id: " + id);
            }
            itemRepository.deleteById(id);
            return ResponseEntity.ok("Item deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the item: " + e.getMessage());
        }
    }

    // Get all items assigned to a particular order
    @GetMapping("/items/order/{orderid}")
    public ResponseEntity<?> getAllItemsByOrderId(@PathVariable("orderid") String id) {
        try {
            List<Item> orderItems = itemRepository.findAllByOrder(id);

            if (orderItems.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No items found for order ID: " + id);
            }

            return ResponseEntity.ok(orderItems);

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching items.");
        }
    }

    // Add list of items to an order
    @PostMapping("/items/order/{orderId}")
    public ResponseEntity<?> updateItemsToOrder(@PathVariable("orderId") String orderId,
                                             @RequestBody List<Item> items) {
        try {
            for (Item item : items) {
                item.setOrder(orderId);
            }

            Iterable<Item> savedItems = itemRepository.saveAll(items);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedItems);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding items to the order.");
        }
    }


    // Delete to remove a particular item from an order
    @DeleteMapping("/items/order/{orderid}")
    public ResponseEntity<?> deleteItemsFromOrder(@PathVariable("orderid") String orderId) {
        try {
            List<Item> itemsToDelete = itemRepository.findAllByOrder(orderId);

            if (itemsToDelete.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No items found for order ID: " + orderId);
            }

            itemRepository.deleteAll(itemsToDelete);
            return ResponseEntity.ok("All items for order ID " + orderId + " have been deleted.");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting items from the order.");
        }
    }


}
