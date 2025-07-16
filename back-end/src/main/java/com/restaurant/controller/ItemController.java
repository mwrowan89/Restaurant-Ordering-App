package com.restaurant.controller;

import com.restaurant.entity.Item;
import com.restaurant.entity.User;
import com.restaurant.repository.ItemRepository;
import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/items")
    public List<Item> getItems(){
        List<Item> items = new ArrayList<>();
        itemRepository.findAll().forEach(items::add);
        return items;
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


}
