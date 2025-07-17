package com.restaurant.controller;

import com.restaurant.entity.User;
import com.restaurant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//TODO add try catches
// TODO Add comments

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User saveUser = userRepository.save(user);
        return new ResponseEntity<>(saveUser, HttpStatus.CREATED);
    }

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

    @PreAuthorize("hasRole('ADMIN')")
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
//    @GetMapping("/test2")
//    public ResponseEntity<String> testEndpoint() {
//        return ResponseEntity.ok("API is working");
//    }
}
