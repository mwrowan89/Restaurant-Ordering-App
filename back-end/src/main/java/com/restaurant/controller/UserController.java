package com.restaurant.controller;

import com.restaurant.entity.User;
import com.restaurant.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserRepository userRepository;

//    @GetMapping("/users")
//    public ResponseEntity<String> getAllUsers() {
//        List<User> users = userRepository.findByRole("ROLE_USER");
//        return ResponseEntity.ok().body(users.get(1));
//    }
@GetMapping("/test2")
public ResponseEntity<String> testEndpoint() {
    return ResponseEntity.ok("API is working properly!");
}
}
