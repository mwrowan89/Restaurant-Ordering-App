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

import com.restaurant.entity.Item;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE) // Use the real database
public class ItemRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    @DisplayName("Should find all items by order ID")
    void testFindAllByOrder() {
        // Arrange
        Item item1 = new Item();
        item1.setOrder("order123");
        item1.setMenuItem("menuitem1");
        item1.setPrice(10.99);
        item1.setFirstName("Customer1");
        entityManager.persist(item1);
        
        Item item2 = new Item();
        item2.setOrder("order123");
        item2.setMenuItem("menuitem2");
        item2.setPrice(12.99);
        item2.setFirstName("Customer1");
        entityManager.persist(item2);
        
        Item item3 = new Item();
        item3.setOrder("order456");
        item3.setMenuItem("menuitem3");
        item3.setPrice(8.99);
        item3.setFirstName("Customer2");
        entityManager.persist(item3);
        
        entityManager.flush();
        
        // Act
        List<Item> order123Items = itemRepository.findAllByOrder("order123");
        List<Item> order456Items = itemRepository.findAllByOrder("order456");
        List<Item> nonexistentItems = itemRepository.findAllByOrder("nonexistent");
        
        // Assert
        assertEquals(2, order123Items.size(), "Should find 2 items for order123");
        assertEquals(1, order456Items.size(), "Should find 1 item for order456");
        assertEquals(0, nonexistentItems.size(), "Should find 0 items for nonexistent order");
        
        assertThat(order123Items).extracting(Item::getMenuItem)
            .containsExactlyInAnyOrder("menuitem1", "menuitem2");
        assertThat(order456Items).extracting(Item::getMenuItem).containsOnly("menuitem3");
    }

    @Test
    @DisplayName("Should save and find item by ID")
    void testSaveAndFindById() {
        // Arrange
        Item item = new Item();
        item.setOrder("order123");
        item.setMenuItem("menuitem1");
        item.setPrice(10.99);
        item.setNotes("No onions");
        item.setFirstName("Customer");
        
        // Act
        Item savedItem = entityManager.persistAndFlush(item);
        Optional<Item> foundItem = itemRepository.findById(String.valueOf(savedItem.getItemId()));
        
        // Assert
        assertThat(foundItem).isPresent();
        assertThat(foundItem.get()).usingRecursiveComparison().isEqualTo(item);
    }
    
    @Test
    @DisplayName("Should update an item")
    void testUpdateItem() {
        // Arrange
        Item item = new Item();
        item.setOrder("order123");
        item.setMenuItem("menuitem1");
        item.setPrice(10.99);
        item.setNotes("Original notes");
        item.setFirstName("Customer");
        Item savedItem = entityManager.persistAndFlush(item);
        
        // Act
        Item itemToUpdate = itemRepository.findById(String.valueOf(savedItem.getItemId())).get();
        itemToUpdate.setNotes("Updated notes");
        itemToUpdate.setPrice(12.99);
        itemRepository.save(itemToUpdate);
        
        // Assert
        Item updatedItem = itemRepository.findById(String.valueOf(savedItem.getItemId())).get();
        assertEquals("Updated notes", updatedItem.getNotes(), "Item notes should be updated");
        assertEquals(12.99, updatedItem.getPrice(), "Item price should be updated");
    }
    
    @Test
    @DisplayName("Should delete an item")
    void testDeleteItem() {
        // Arrange
        Item item = new Item();
        item.setOrder("order123");
        item.setMenuItem("menuitem1");
        item.setPrice(10.99);
        item.setFirstName("Customer");
        Item savedItem = entityManager.persistAndFlush(item);
        
        // Act
        itemRepository.deleteById(String.valueOf(savedItem.getItemId()));
        Optional<Item> foundItem = itemRepository.findById(String.valueOf(savedItem.getItemId()));
        
        // Assert
        assertThat(foundItem).isEmpty();
    }
    
    @Test
    @DisplayName("Should delete all items for an order")
    void testDeleteAllByOrder() {
        // Arrange
        Item item1 = new Item();
        item1.setOrder("deleteOrder");
        item1.setMenuItem("menuitem1");
        item1.setPrice(10.99);
        item1.setFirstName("Customer");
        entityManager.persist(item1);
        
        Item item2 = new Item();
        item2.setOrder("deleteOrder");
        item2.setMenuItem("menuitem2");
        item2.setPrice(12.99);
        item2.setFirstName("Customer");
        entityManager.persist(item2);
        
        Item item3 = new Item();
        item3.setOrder("keepOrder");
        item3.setMenuItem("menuitem3");
        item3.setPrice(8.99);
        item3.setFirstName("Customer");
        entityManager.persist(item3);
        
        entityManager.flush();
        
        // Act
        List<Item> itemsToDelete = itemRepository.findAllByOrder("deleteOrder");
        itemRepository.deleteAll(itemsToDelete);
        
        // Assert
        List<Item> remainingDeleteOrderItems = itemRepository.findAllByOrder("deleteOrder");
        List<Item> remainingKeepOrderItems = itemRepository.findAllByOrder("keepOrder");
        
        assertEquals(0, remainingDeleteOrderItems.size(), "Should have deleted all items for deleteOrder");
        assertEquals(1, remainingKeepOrderItems.size(), "Should not have deleted items for keepOrder");
    }
}
