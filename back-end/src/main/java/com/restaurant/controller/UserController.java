package com.restaurant.controller;

import com.restaurant.entity.User;
import com.restaurant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    //TODO add try catches
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok().body(user.get());
        } else {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ofNullable(user.get());
    }

    @GetMapping("/test2")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("API is working properly!");
    }
}
