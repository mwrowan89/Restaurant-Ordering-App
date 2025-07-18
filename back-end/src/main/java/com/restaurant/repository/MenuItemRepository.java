package com.restaurant.repository;

import com.restaurant.entity.MenuItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//TODO update methods to jpa naming convention
//TODO comment all methods
@Repository
public interface MenuItemRepository extends JpaRepository<MenuItems, String> {
    List<MenuItems> findByCategory(String category);
}
