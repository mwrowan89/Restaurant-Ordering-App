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
import com.restaurant.entity.User;
import com.restaurant.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private User testUser;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
        
        // Create a test user
        testUser = new User();
        testUser.setUserId(1L);
        testUser.setUsername("testuser");
        testUser.setPassword("password");
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setPhone("1234567890");
        testUser.setEmail("test@example.com");
        testUser.setImageUrl("http://example.com/image.jpg");
        testUser.setPan("1234");
        testUser.setExpiryMonth(12);
        testUser.setExpiryYear(2025);
        testUser.setRole("USER");
    }

    @Test
    @DisplayName("GET /api/users - Should return all users")
    void testGetAllUsers() throws Exception {
        // Arrange
        List<User> users = new ArrayList<>();
        users.add(testUser);
        
        when(userRepository.findAll()).thenReturn(users);

        // Act & Assert
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username", is(testUser.getUsername())));

        verify(userRepository).findAll();
    }
    
    @Test
    @DisplayName("GET /api/users - Should return empty list when no users exist")
    void testGetAllUsersEmpty() throws Exception {
        // Arrange
        List<User> users = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(users);

        // Act & Assert
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(userRepository).findAll();
    }

    @Test
    @DisplayName("POST /api/users - Should create a new user")
    void testCreateUser() throws Exception {
        // Arrange
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act & Assert
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is(testUser.getUsername())));

        verify(userRepository).save(any(User.class));
    }
    
    @Test
    @DisplayName("POST /api/users - Should handle invalid JSON")
    void testCreateUserInvalidJson() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("Invalid JSON"))
                .andExpect(status().isBadRequest());
                
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("GET /api/users/{id} - Should return user by ID")
    void testGetUserById() throws Exception {
        // Arrange
        when(userRepository.findById("1")).thenReturn(Optional.of(testUser));

        // Act & Assert
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(testUser.getUsername())))
                .andExpect(jsonPath("$.firstName", is(testUser.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(testUser.getLastName())));

        verify(userRepository).findById("1");
    }

    @Test
    @DisplayName("GET /api/users/{id} - Should return 404 when user not found")
    void testGetUserByIdNotFound() throws Exception {
        // Arrange
        when(userRepository.findById("999")).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/users/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("User not found")));

        verify(userRepository).findById("999");
    }
    
    @Test
    @DisplayName("GET /api/users/{id} - Should handle repository exception")
    void testGetUserByIdException() throws Exception {
        // Arrange
        when(userRepository.findById(anyString())).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("An error occurred while fetching the user")));

        verify(userRepository).findById("1");
    }

    @Test
    @DisplayName("PUT /api/users/{id} - Should update user")
    void testUpdateUser() throws Exception {
        // Arrange
        User updatedUser = testUser;
        updatedUser.setFirstName("Updated");
        updatedUser.setLastName("Name");
        
        when(userRepository.findById("1")).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        // Act & Assert
        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Updated")))
                .andExpect(jsonPath("$.lastName", is("Name")));

        verify(userRepository).findById("1");
        verify(userRepository).save(any(User.class));
    }
    
    @Test
    @DisplayName("PUT /api/users/{id} - Should return 404 when user not found")
    void testUpdateUserNotFound() throws Exception {
        // Arrange
        User updatedUser = testUser;
        updatedUser.setFirstName("Updated");
        updatedUser.setLastName("Name");
        
        when(userRepository.findById("999")).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(put("/api/users/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("User not found")));

        verify(userRepository).findById("999");
        verify(userRepository, never()).save(any(User.class));
    }
    
    @Test
    @DisplayName("PUT /api/users/{id} - Should handle repository find exception")
    void testUpdateUserFindException() throws Exception {
        // Arrange
        User updatedUser = testUser;
        updatedUser.setFirstName("Updated");
        
        when(userRepository.findById(anyString())).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("An error occurred while updating the user")));

        verify(userRepository).findById("1");
        verify(userRepository, never()).save(any(User.class));
    }
    
    @Test
    @DisplayName("PUT /api/users/{id} - Should handle repository save exception")
    void testUpdateUserSaveException() throws Exception {
        // Arrange
        User updatedUser = testUser;
        updatedUser.setFirstName("Updated");
        
        when(userRepository.findById("1")).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("An error occurred while updating the user")));

        verify(userRepository).findById("1");
        verify(userRepository).save(any(User.class));
    }
    
    @Test
    @DisplayName("PUT /api/users/{id} - Should handle invalid JSON")
    void testUpdateUserInvalidJson() throws Exception {
        // Act & Assert
        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("Invalid JSON"))
                .andExpect(status().isBadRequest());
                
        verify(userRepository, never()).findById(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("DELETE /api/users/{id} - Should delete user")
    void testDeleteUser() throws Exception {
        // Arrange
        when(userRepository.findById("1")).thenReturn(Optional.of(testUser));
        doNothing().when(userRepository).deleteById("1");

        // Act & Assert
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("User deleted successfully")));

        verify(userRepository).findById("1");
        verify(userRepository).deleteById("1");
    }
    
    @Test
    @DisplayName("DELETE /api/users/{id} - Should return 404 when user not found")
    void testDeleteUserNotFound() throws Exception {
        // Arrange
        when(userRepository.findById("999")).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(delete("/api/users/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("User not found")));

        verify(userRepository).findById("999");
        verify(userRepository, never()).deleteById(anyString());
    }
    
    @Test
    @DisplayName("DELETE /api/users/{id} - Should handle repository find exception")
    void testDeleteUserFindException() throws Exception {
        // Arrange
        when(userRepository.findById(anyString())).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("An error occurred while deleting the user")));

        verify(userRepository).findById("1");
        verify(userRepository, never()).deleteById(anyString());
    }
    
    @Test
    @DisplayName("DELETE /api/users/{id} - Should handle repository delete exception")
    void testDeleteUserDeleteException() throws Exception {
        // Arrange
        when(userRepository.findById("1")).thenReturn(Optional.of(testUser));
        doThrow(new RuntimeException("Database error")).when(userRepository).deleteById("1");

        // Act & Assert
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("An error occurred while deleting the user")));

        verify(userRepository).findById("1");
        verify(userRepository).deleteById("1");
    }
}
