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

import com.restaurant.entity.MenuItems;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE) // Use the real database
public class MenuItemRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Test
    @DisplayName("Should find menu items by category")
    void testFindByCategory() {
        // Arrange
        MenuItems burgerItem1 = new MenuItems();
        burgerItem1.setName("Classic Burger");
        burgerItem1.setDescription("A classic beef burger");
        burgerItem1.setCategory("burger");
        burgerItem1.setPrice(10.99);
        burgerItem1.setAvailable(true);
        entityManager.persist(burgerItem1);
        
        MenuItems burgerItem2 = new MenuItems();
        burgerItem2.setName("Cheese Burger");
        burgerItem2.setDescription("A classic burger with cheese");
        burgerItem2.setCategory("burger");
        burgerItem2.setPrice(12.99);
        burgerItem2.setAvailable(true);
        entityManager.persist(burgerItem2);
        
        MenuItems dessertItem = new MenuItems();
        dessertItem.setName("Ice Cream");
        dessertItem.setDescription("Vanilla ice cream");
        dessertItem.setCategory("dessert");
        dessertItem.setPrice(5.99);
        dessertItem.setAvailable(true);
        entityManager.persist(dessertItem);
        
        entityManager.flush();
        
        // Act
        List<MenuItems> burgerItems = menuItemRepository.findByCategory("burger");
        List<MenuItems> dessertItems = menuItemRepository.findByCategory("dessert");
        List<MenuItems> drinkItems = menuItemRepository.findByCategory("drink");
        
        // Assert
        assertEquals(2, burgerItems.size(), "Should find 2 burger items");
        assertEquals(1, dessertItems.size(), "Should find 1 dessert item");
        assertEquals(0, drinkItems.size(), "Should find 0 drink items");
        
        assertThat(burgerItems).extracting(MenuItems::getName)
            .containsExactlyInAnyOrder("Classic Burger", "Cheese Burger");
        assertThat(dessertItems).extracting(MenuItems::getName).containsOnly("Ice Cream");
    }

    @Test
    @DisplayName("Should save and find menu item by ID")
    void testSaveAndFindById() {
        // Arrange
        MenuItems menuItem = new MenuItems();
        menuItem.setName("Test Item");
        menuItem.setDescription("A test menu item");
        menuItem.setCategory("test");
        menuItem.setPrice(9.99);
        menuItem.setImageurl("http://example.com/test.jpg");
        menuItem.setAvailable(true);
        
        // Act
        MenuItems savedItem = entityManager.persistAndFlush(menuItem);
        Optional<MenuItems> foundItem = menuItemRepository.findById(savedItem.getId().toString());
        
        // Assert
        assertThat(foundItem).isPresent();
        assertThat(foundItem.get()).usingRecursiveComparison().isEqualTo(menuItem);
    }
    
    @Test
    @DisplayName("Should update a menu item")
    void testUpdateMenuItem() {
        // Arrange
        MenuItems menuItem = new MenuItems();
        menuItem.setName("Original Name");
        menuItem.setDescription("Original description");
        menuItem.setCategory("test");
        menuItem.setPrice(9.99);
        menuItem.setAvailable(true);
        MenuItems savedItem = entityManager.persistAndFlush(menuItem);
        
        // Act
        MenuItems itemToUpdate = menuItemRepository.findById(savedItem.getId().toString()).get();
        itemToUpdate.setName("Updated Name");
        itemToUpdate.setPrice(14.99);
        menuItemRepository.save(itemToUpdate);
        
        // Assert
        MenuItems updatedItem = menuItemRepository.findById(savedItem.getId().toString()).get();
        assertEquals("Updated Name", updatedItem.getName(), "Menu item name should be updated");
        assertEquals(14.99, updatedItem.getPrice(), "Menu item price should be updated");
    }
    
    @Test
    @DisplayName("Should delete a menu item")
    void testDeleteMenuItem() {
        // Arrange
        MenuItems menuItem = new MenuItems();
        menuItem.setName("Item to Delete");
        menuItem.setDescription("This item will be deleted");
        menuItem.setCategory("test");
        menuItem.setPrice(9.99);
        menuItem.setAvailable(true);
        MenuItems savedItem = entityManager.persistAndFlush(menuItem);
        
        // Act
        menuItemRepository.deleteById(savedItem.getId().toString());
        Optional<MenuItems> foundItem = menuItemRepository.findById(savedItem.getId().toString());
        
        // Assert
        assertThat(foundItem).isEmpty();
    }
}
