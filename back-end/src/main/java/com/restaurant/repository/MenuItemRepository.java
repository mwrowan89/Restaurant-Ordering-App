package com.restaurant.repository;

import com.restaurant.entity.MenuItems;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends CrudRepository<MenuItems, String> {
    // Find menu items by their category
    List<MenuItems> findByCategory(String category);
}
