package com.restaurant.repository;

import com.restaurant.entity.Item;
import com.restaurant.entity.Orders;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, String> {
    List<Item> findByOrder(Orders order);
}
