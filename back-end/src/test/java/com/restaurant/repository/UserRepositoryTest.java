package com.restaurant.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.restaurant.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE) // Use the real database
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should find users by role")
    void testFindByRole() {
        // Arrange
        User adminUser = new User();
        adminUser.setUsername("admin1");
        adminUser.setPassword("password");
        adminUser.setFirstName("Admin");
        adminUser.setLastName("User");
        adminUser.setRole("ADMIN");
        entityManager.persist(adminUser);
        
        User regularUser1 = new User();
        regularUser1.setUsername("user1");
        regularUser1.setPassword("password");
        regularUser1.setFirstName("Regular");
        regularUser1.setLastName("User1");
        regularUser1.setRole("USER");
        entityManager.persist(regularUser1);
        
        User regularUser2 = new User();
        regularUser2.setUsername("user2");
        regularUser2.setPassword("password");
        regularUser2.setFirstName("Regular");
        regularUser2.setLastName("User2");
        regularUser2.setRole("USER");
        entityManager.persist(regularUser2);
        
        entityManager.flush();
        
        // Act
        List<User> adminUsers = userRepository.findByRole("ADMIN");
        List<User> regularUsers = userRepository.findByRole("USER");
        
        // Assert
        assertEquals(1, adminUsers.size(), "Should find 1 admin user");
        assertEquals(2, regularUsers.size(), "Should find 2 regular users");
        assertThat(adminUsers).extracting(User::getUsername).containsOnly("admin1");
        assertThat(regularUsers).extracting(User::getUsername).containsExactlyInAnyOrder("user1", "user2");
    }

    @Test
    @DisplayName("Should save and find user by ID")
    void testSaveAndFindById() {
        // Arrange
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmail("test@example.com");
        user.setPhone("1234567890");
        user.setRole("USER");
        
        // Act
        User savedUser = entityManager.persistAndFlush(user);
        Optional<User> foundUser = userRepository.findById(savedUser.getUserId().toString());
        
        // Assert
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get()).usingRecursiveComparison().isEqualTo(user);
    }
    
    @Test
    @DisplayName("Should update a user")
    void testUpdateUser() {
        // Arrange
        User user = new User();
        user.setUsername("updateuser");
        user.setPassword("password");
        user.setFirstName("Before");
        user.setLastName("Update");
        user.setRole("USER");
        User savedUser = entityManager.persistAndFlush(user);
        
        // Act
        User userToUpdate = userRepository.findById(savedUser.getUserId().toString()).get();
        userToUpdate.setFirstName("After");
        userToUpdate.setLastName("Updated");
        userRepository.save(userToUpdate);
        
        // Assert
        User updatedUser = userRepository.findById(savedUser.getUserId().toString()).get();
        assertEquals("After", updatedUser.getFirstName(), "User first name should be updated");
        assertEquals("Updated", updatedUser.getLastName(), "User last name should be updated");
    }
    
    @Test
    @DisplayName("Should delete a user")
    void testDeleteUser() {
        // Arrange
        User user = new User();
        user.setUsername("deleteuser");
        user.setPassword("password");
        user.setFirstName("Delete");
        user.setLastName("User");
        user.setRole("USER");
        User savedUser = entityManager.persistAndFlush(user);
        
        // Act
        userRepository.deleteById(savedUser.getUserId().toString());
        Optional<User> foundUser = userRepository.findById(savedUser.getUserId().toString());
        
        // Assert
        assertThat(foundUser).isEmpty();
    }
}
