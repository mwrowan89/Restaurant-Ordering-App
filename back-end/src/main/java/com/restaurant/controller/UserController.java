package com.restaurant.controller;

import com.restaurant.entity.Orders;
import com.restaurant.entity.User;
import com.restaurant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//TODO add custom exception classes

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Get all users
    // TODO add auth and uncomment
    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = new ArrayList<>();
            userRepository.findAll().forEach(users::add);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while retrieving users.");
        }
    }

    // Create a new user
    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User saveUser = userRepository.save(user);
            return new ResponseEntity<>(saveUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating the user: " + e.getMessage());
        }
    }

    // Get user by ID
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable(value = "id") String id) {
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                return ResponseEntity.ok(user.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found with id: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the user: " + e.getMessage());
        }
    }

    // Update user by ID
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable(value = "id") String id,
            @RequestBody User userDetails) {
        try {
            Optional<User> optionalUser = userRepository.findById(id);
            if (!optionalUser.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found with id: " + id);
            }

            User user = optionalUser.get();

            user.setUsername(userDetails.getUsername());
            user.setPassword(userDetails.getPassword());
            user.setFirstName(userDetails.getFirstName());
            user.setLastName(userDetails.getLastName());
            user.setPhone(userDetails.getPhone());
            user.setEmail(userDetails.getEmail());
            user.setImageUrl(userDetails.getImageUrl());
            user.setPan(userDetails.getPan());
            user.setExpiryMonth(userDetails.getExpiryMonth());
            user.setExpiryYear(userDetails.getExpiryYear());

            User updatedUser = userRepository.save(user);
            return ResponseEntity.ok(updatedUser);

        } catch (Exception e) {
            // TODO add exception classes
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the user: " + e.getMessage());
        }
    }

    // Delete user by ID
    // TODO add auth and uncomment
    // @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") String id) {
        try {
            Optional<User> optionalUser = userRepository.findById(id);
            if (!optionalUser.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found with id: " + id);
            }
            userRepository.deleteById(id);
            return ResponseEntity.ok("User deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the user: " + e.getMessage());
        }
    }
}
